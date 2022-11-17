package com.shelter.testroom

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

/**
 * @author: Shelter
 * Create time: 2022/11/14, 19:33.
 */
class DBHelper//判断目录是否存在，不存在则创建该目录
//允许在主线程中查询
private constructor(context: Context) {
    private var db: AppRoomDatabase
    private var DB_PATH =
        context.filesDir.absolutePath + File.separator + "tl" + File.separator + "db"
    private var DB_NAME = DB_PATH + File.separator + "tl_event.db"

    companion object {
        private var mInstance: DBHelper? = null

        fun getInstance(context: Context): DBHelper {
            if (mInstance == null) {
                synchronized(DBHelper::class.java) {
                    if (mInstance == null) {
                        mInstance = DBHelper(context.applicationContext)
                    }
                }
            }
            return mInstance!!
        }
    }

    init {
        val dir = File(DB_PATH)
        Log.d("Shelter", "DBHelper path = ${dir.absolutePath} exists=${dir.exists()}")
        if (!dir.exists()) {
            val result = dir.mkdirs()
            Log.d("Shelter", "DBHelper result = $result")
        }
        val dbFile = File(DB_NAME)
        val result = dbFile.createNewFile()
        Log.d("Shelter", "DBHelper dbFilePath:${dbFile}, exists:${dbFile.exists()}, result=$result")
        db = Room.databaseBuilder(context, AppRoomDatabase::class.java, DB_NAME)
            .allowMainThreadQueries().setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .fallbackToDestructiveMigration()
            .build()
        Log.d("Shelter", "DBHelper db.isOpen=${db.isOpen}")
    }

    fun getuserEventDAO(): UserEventDAO {
        return db.userEventDAO()
    }

    fun insertUserEvent(userEvent: UserEvent): Long {
        return getuserEventDAO().insertUserEvent(userEvent)
    }

    fun deleteUserEvent(userEvent: UserEvent) {
        val rows = getuserEventDAO().deleteUserEvent(userEvent)
        Log.d("Shelter", "DBHelper deleteBook rows:$rows")
    }

    fun updateUserEvent(userEvent: UserEvent) {
        getuserEventDAO().updateUserEvent(userEvent)
    }

    fun queryUserEvent(id: Int): UserEvent {
        return getuserEventDAO().queryUserEvent(id)
    }

    fun queryAll(): List<UserEvent> {
        return getuserEventDAO().queryAll()
    }

    fun deleteAll() {
        val rows = getuserEventDAO().deleteAll()
        Log.d("Shelter", "DBHelper deleteAll rows:$rows")
    }


}