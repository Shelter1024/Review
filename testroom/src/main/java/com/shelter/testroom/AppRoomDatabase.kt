package com.shelter.testroom

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author: Shelter
 * Create time: 2022/11/14, 18:28.
 */
@Database(entities = [UserEvent::class], version = 1)
abstract class AppRoomDatabase: RoomDatabase() {

    abstract fun userEventDAO() : UserEventDAO

}