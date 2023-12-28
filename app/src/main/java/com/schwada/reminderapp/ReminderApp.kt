package com.schwada.reminderapp

import android.app.Application
import com.schwada.reminderapp.data.local.AppDatabase
import com.schwada.reminderapp.data.local.reminder.ReminderRepository

class ReminderApp: Application() {

    lateinit var appDatabase: AppDatabase private set
    lateinit var reminderRepository: ReminderRepository private set

    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase.getDatabase(this)
        reminderRepository = ReminderRepository(appDatabase.reminderDao())
    }
}