package com.peerbitskuldeep.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        btnTime.setOnClickListener {

            showTimePicker()

        }

        btnAlarm.setOnClickListener {
            setAlarm()
        }

        btnCancel.setOnClickListener {
            cancelAlarm()
        }

    }

    private fun cancelAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent =  Intent(this, AlarmService::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,0, intent, 0)

        alarmManager.cancel(pendingIntent)
    }

    private fun setAlarm() {

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent =  Intent(this, AlarmService::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,0, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(this, "Alarm set successfully!", Toast.LENGTH_SHORT).show()

    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "androidnotification"
            val description = "My Notification"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel("cid", name, importance)
            channel.description = description

            val notificationManager = getSystemService( NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showTimePicker() {

        picker = MaterialTimePicker.Builder()
            .setHour(12)
            .setMinute(0)
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setTitleText("Select alarm time")
            .build()

        picker.show(supportFragmentManager,"cid")

        picker.addOnPositiveButtonClickListener {

            if (picker.hour > 12)
            {
                tvTime.text = "${picker.hour-12} : ${picker.minute} PM"
            }
            else{
                tvTime.text = "${picker.hour} : ${picker.minute} AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

        }

    }
}