package com.schwada.reminderapp.ui.create

import android.app.Service
import android.content.Context
import android.content.Intent

import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator

import com.schwada.reminderapp.data.local.AppDatabase
import com.schwada.reminderapp.data.local.reminder.ReminderDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderService : Service() {

    private lateinit var reminderDao: ReminderDao
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        // Initialize the Room database
        val reminderDatabase = AppDatabase.getDatabase(applicationContext)
        reminderDao = reminderDatabase.reminderDao()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val reminderId = intent?.getLongExtra("reminderId", -1)
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(500, 1000, 500, 1000), -1))

        if (reminderId != null && reminderId.toInt() != -1) setArchived(reminderId)

        stopSelf()
        return START_NOT_STICKY
    }

    fun setArchived(reminderId: Long) =  serviceScope.launch {
        reminderDao.updateArchivedState(reminderId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
