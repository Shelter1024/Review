package com.shelter.testroom

import androidx.room.*

/**
 * @author: Shelter
 * Create time: 2022/11/14, 17:57.
 */
@Dao
abstract class UserEventDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUserEvent(userEvent: UserEvent): Long

    @Delete
    abstract fun deleteUserEvent(userEvent: UserEvent): Int

    @Update
    abstract fun updateUserEvent(userEvent: UserEvent)

    @Query("select * from UserEvent where id = :id")
    abstract fun queryUserEvent(id: Int): UserEvent

    @Query("select * from UserEvent")
    abstract fun queryAll(): List<UserEvent>

    @Query("delete from UserEvent")
    abstract fun deleteAll(): Int

}