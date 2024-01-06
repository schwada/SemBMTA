package com.schwada.reminderapp.ui.create

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.schwada.reminderapp.R

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        val notifyUsingAlarm = intent?.getBooleanExtra("reminderAlarm", false);

        Log.i("schwschwa", "test")
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = "alarm_id"
        val notificationBuilder = NotificationCompat.Builder(context!!, channelId)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(intent?.getStringExtra("reminderTitle"))
            .setContentText(intent?.getStringExtra("reminderDesc"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

//        val serviceIntent = Intent(context, ReminderService::class.java)
//        context?.startService(serviceIntent)


        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notificationBuilder.build())
    }





}
