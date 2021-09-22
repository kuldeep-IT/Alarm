package com.peerbitskuldeep.alarm

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmService : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        //create notification
        val i = Intent(context,SecondActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context,0,i,0)

        val notificationBuilder = NotificationCompat.Builder(context!!,"cid")
            .setSmallIcon(
                R.drawable.ic_launcher_background
            )
            .setAutoCancel(true)
            .setContentTitle("Hey WakeUp")
            .setContentText("Hurry Up!")
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(111, notificationBuilder.build())
    }

}