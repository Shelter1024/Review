package com.shelter.testroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: Shelter
 * Create time: 2022/11/14, 17:02.
 */
@Entity(tableName = "UserEvent")
class UserEvent {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    //App启动ID
    @ColumnInfo(name = "app_launch_id")
    var appLaunchId: String? = null

    //事件ID
    @ColumnInfo(name = "event_id")
    var eventId: String? = null

    //事件名称
    @ColumnInfo(name = "event_name")
    var eventName: String? = null

    //本地时间 elapsedRealtime
    @ColumnInfo(name = "event_timestamp")
    var eventTimestamp: Long? = null

    //服务器时间 servertime
    @ColumnInfo(name = "event_server_timestamp")
    var eventServerTimestamp: Long? = null

    //事件数据:json
    @ColumnInfo(name = "event_data")
    var eventData: String? = null

}