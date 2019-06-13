package com.isel.ps.sds.view.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.quiz.QuizViewModel
import kotlinx.android.synthetic.main.activity_notification_schedule.*

class NotificationActivity: BaseActivity<QuizViewModel>() {

    override fun defineViewModel(): Class<QuizViewModel> = com.isel.ps.sds.view.quiz.QuizViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_notification_schedule
    override fun doOnCreate(savedInstanceState: Bundle?) {
        timePickerStart.setIs24HourView(true);


        switchNotification.setOnClickListener {
            if(switchNotification.isChecked) {
                var hour: Int
                var minute: Int
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = timePickerStart.hour
                    minute = timePickerStart.minute
                } else {
                    hour = timePickerStart.currentHour
                    minute = timePickerStart.currentMinute
                }
                createNotificationChannel()
                startAlarm(hour, minute)
            }else{cancelAlarm()}

        }

    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "myChanel"//getString(R.string.channel_name)
            val descriptionText = "Description test"//getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("ch", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startAlarm(hour:Int, minute:Int) {
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val myIntent: Intent
        val pendingIntent: PendingIntent
        val alarmStartTime: Calendar
        var now: Calendar

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            alarmStartTime = Calendar.getInstance();
            now = Calendar.getInstance();
            alarmStartTime.set(Calendar.HOUR_OF_DAY, hour);
            alarmStartTime.set(Calendar.MINUTE, minute);
            alarmStartTime.set(Calendar.SECOND, 0);

            if (now.after(alarmStartTime)) {
                Log.d("Hey", "Added a day");
                alarmStartTime.add(Calendar.DATE, 1);

            }
            myIntent = Intent(this@NotificationActivity, AlarmNotificationReceiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0)

                manager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    alarmStartTime.getTimeInMillis(),
                    //calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )

        } else { }
    }

    private fun cancelAlarm(){
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var myIntent = Intent(this@NotificationActivity, AlarmNotificationReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0)
        manager.cancel(pendingIntent)
    }

}