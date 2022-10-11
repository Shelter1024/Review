package com.example.testcalendar

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract
import android.text.TextUtils
import java.util.*

/**
 * @author: Shelter
 * Create time: 2022/9/21, 10:33.
 */
object CalendarReminderUtil {

    private const val CALENDER_URL = "content://com.android.calendar/calendars"
    private const val CALENDER_EVENT_URL = "content://com.android.calendar/events"
    private const val CALENDER_REMINDER_URL = "content://com.android.calendar/reminders"

    private const val CALENDARS_NAME = "Shelter"
    private const val CALENDARS_ACCOUNT_NAME = "SHELTER@shelter.com"
    private const val CALENDARS_ACCOUNT_TYPE = "com.android.shelter"
    private const val CALENDARS_DISPLAY_NAME = "我的账户"
    private val eventTimes = arrayOf(8, 12, 17, 20)
    private val titleList =
        arrayListOf("【极速刷】咚咚咚，刷新啦！打开APP领取奖励~", "【极速刷】连续签到领现金红包，打开APP去签到！！", "【极速刷】看资讯刷视频，每日提现~", "【极速刷】看资讯刷视频，每日提现~")

    /**
     * 检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
     * 获取账户成功返回账户id，否则返回-1
     */
    private fun checkAndAddCalendarAccount(context: Context): Int {
        val oldId = checkCalendarAccount(context)
        return if (oldId >= 0) {
            oldId
        } else {
            val addId = addCalendarAccount(context)
            if (addId >= 0) {
                checkCalendarAccount(context)
            } else {
                -1
            }
        }
    }

    /**
     * 检查是否存在现有账户，存在则返回账户id，否则返回-1
     */
    private fun checkCalendarAccount(context: Context): Int {
        val userCursor: Cursor? =
            context.contentResolver.query(Uri.parse(CALENDER_URL), null, null, null, null)
        return userCursor.use { userCursor ->
            if (userCursor == null) { //查询返回空值
                return -1
            }
            val count: Int = userCursor.count
            if (count > 0) { //存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst()
                val columnIndex = userCursor.getColumnIndex(CalendarContract.Calendars._ID)
                if (columnIndex < 0) {
                    return -1
                }
                userCursor.getInt(columnIndex)
            } else {
                -1
            }
        }
    }

    /**
     * 添加日历账户，账户创建成功则返回账户id，否则返回-1
     */
    private fun addCalendarAccount(context: Context): Long {
        val timeZone: TimeZone = TimeZone.getDefault()
        val value = ContentValues()
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME)
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME)
        value.put(CalendarContract.Calendars.VISIBLE, 1)
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
        value.put(
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
            CalendarContract.Calendars.CAL_ACCESS_OWNER
        )
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1)
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.id)
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME)
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0)
        var calendarUri: Uri = Uri.parse(CALENDER_URL)
        calendarUri = calendarUri.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
            .build()
        val result: Uri? = context.contentResolver.insert(calendarUri, value)
        return if (result == null) -1 else ContentUris.parseId(result)
    }

    /**
     * 添加日历事件
     */
    fun addCalendarEvent(context: Context?) {
        if (context == null) {
            return
        }
        val calId = checkAndAddCalendarAccount(context) //获取日历账户的id
        if (calId < 0) { //获取账户id失败直接返回，添加日历事件失败
            return
        }

        //添加日历事件
        val mCalendar: Calendar = Calendar.getInstance()
        mCalendar.set(Calendar.HOUR_OF_DAY, 0)
        mCalendar.set(Calendar.MINUTE, 0)
        mCalendar.set(Calendar.SECOND, 0)
        var startTime = mCalendar.timeInMillis
        for (day in 0..13) {
            startTime += (day * 24 * 60 * 60 * 1000)
            for (count in 0..3) {
                mCalendar.timeInMillis = (startTime + eventTimes[count] * 60 * 60 * 1000)//设置开始时间
                val start: Long = mCalendar.time.time
                mCalendar.timeInMillis = mCalendar.timeInMillis + 10 * 60 * 1000 //设置终止时间，开始时间加10分钟
                val end: Long = mCalendar.time.time
                val event = ContentValues()
                event.put("title", titleList[count])
                event.put("description", "")
                event.put("calendar_id", calId) //插入账户的id
                event.put(CalendarContract.Events.DTSTART, start)
                event.put(CalendarContract.Events.DTEND, end)
                event.put(CalendarContract.Events.HAS_ALARM, 1) //设置有闹钟提醒
                event.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai") //这个是时区，必须有
                val newEvent: Uri =
                    context.contentResolver.insert(Uri.parse(CALENDER_EVENT_URL), event)
                        ?: //添加日历事件失败直接返回
                        return //添加事件

                //事件提醒的设定
                val values = ContentValues()
                values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newEvent))
                // 提前previousDate天有提醒
                values.put(CalendarContract.Reminders.MINUTES, 10)
                values.put(
                    CalendarContract.Reminders.METHOD,
                    CalendarContract.Reminders.METHOD_ALERT
                )
                //添加事件提醒
                context.contentResolver.insert(Uri.parse(CALENDER_REMINDER_URL), values)
            }
        }

    }

    /**
     * 删除日历事件
     */
    fun deleteCalendarEvent(context: Context?, title: String) {
        if (context == null) {
            return
        }
        val eventCursor: Cursor? = context.contentResolver
            .query(Uri.parse(CALENDER_EVENT_URL), null, null, null, null)
        eventCursor.use { eventCursor ->
            if (eventCursor == null) { //查询返回空值
                return
            }
            if (eventCursor.count > 0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                eventCursor.moveToFirst()
                while (!eventCursor.isAfterLast) {
                    val columnIndex = eventCursor.getColumnIndex("title")
                    if (columnIndex < 0) {
                        return
                    }
                    val eventTitle: String =
                        eventCursor.getString(columnIndex)
                    if (!TextUtils.isEmpty(title) && title == eventTitle) {
                        val columnIndex = eventCursor.getColumnIndex(CalendarContract.Calendars._ID)
                        if (columnIndex < 0) {
                            return
                        }
                        val id: Int =
                            eventCursor.getInt(columnIndex) //取得id
                        val deleteUri: Uri =
                            ContentUris.withAppendedId(Uri.parse(CALENDER_EVENT_URL), id.toLong())
                        val rows: Int = context.contentResolver.delete(deleteUri, null, null)
                        if (rows == -1) { //事件删除失败
                            return
                        }
                    }
                    eventCursor.moveToNext()
                }
            }
        }
    }
}