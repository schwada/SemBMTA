package com.schwada.reminderapp.data.local.reminder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders WHERE archived = 0")
    fun getActiveReminders(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE archived = 1")
    fun getArchivedReminders(): Flow<List<Reminder>>

    @Query("UPDATE reminders SET archived = 1 WHERE id = :reminderId")
    suspend fun updateArchivedState(reminderId: Long)

    @Query("SELECT * FROM reminders WHERE id = :reminderId")
    suspend fun getReminderById(reminderId: Long): Reminder?
    @Delete
    suspend fun deleteReminder(reminder: Reminder)

}
