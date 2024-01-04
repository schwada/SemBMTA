package com.schwada.reminderapp.data.local.reminder

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val reminderDao: ReminderDao) {


    val activeReminders: Flow<List<Reminder>> = reminderDao.getActiveReminders()
    val archivedReminders: Flow<List<Reminder>> = reminderDao.getArchivedReminders()

    @WorkerThread
    suspend fun saveReminder(reminder: Reminder) {
        reminderDao.insertReminder(reminder)
    }

    @WorkerThread
    suspend fun getReminderById(reminderId: Long): Reminder? {
        return reminderDao.getReminderById(reminderId)
    }

    @WorkerThread
    suspend fun deleteReminder(reminder: Reminder){
        reminderDao.deleteReminder(reminder)
    }



}
