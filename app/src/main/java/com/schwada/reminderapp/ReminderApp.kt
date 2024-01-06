package com.schwada.reminderapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.schwada.reminderapp.data.local.AppDatabase
import com.schwada.reminderapp.data.local.reminder.ReminderRepository

class ReminderApp: Application() {

    lateinit var appDatabase: AppDatabase private set
    lateinit var reminderRepository: ReminderRepository private set

    override fun onCreate() {
        super.onCreate()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("alarm_id","alarm_name",NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)


        appDatabase = AppDatabase.getDatabase(this)
        reminderRepository = ReminderRepository(appDatabase.reminderDao())
    }
}