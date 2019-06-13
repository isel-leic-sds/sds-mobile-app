package com.isel.ps.sds.view.login

import android.content.Intent
import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import android.os.CountDownTimer
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import com.example.myapplication.LoginViewModel
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.profile.ProfileActivity
import com.isel.ps.sds.view.menu.MenuActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.SystemClock
import android.app.PendingIntent
import com.isel.ps.sds.view.notification.AlarmNotificationReceiver
import android.content.Context
import android.icu.util.Calendar
import android.os.Build


class LoginActivity : BaseActivity<LoginViewModel>() {
    private val loginFactory: LoginFactory = LoginFactory()

    override fun defineViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_login

    override fun doOnCreate(savedInstanceState: Bundle?) {
        viewModel.getLoginState().observe(this, Observer<Boolean> {
            if (it) {
                onSuccess()
            } else {
                loginBtn.isEnabled = true
            }
        })

        viewModel.getErrorMessage().observe(this, Observer<String> {
            Toast.makeText(this, "Login failed. Try Again!: $it", Toast.LENGTH_LONG).show()
        })

        loginBtn.setOnClickListener {
            loginBtn.isEnabled = false
            val userName: String = userName.text.toString()
            val password = password.text.toString()

            viewModel.tryLogin(loginFactory.getLogin(userName, password))
        }

        object : CountDownTimer(5000, 1000) {
            override fun onFinish() {
                sdsTextView.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
                rootView.setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.colorSplashText))
                sdsImageView.setImageResource(R.drawable.sds)
                startAnimation()
            }

            override fun onTick(p0: Long) {}
        }.start()
    }

    private fun onSuccess() {
        Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show()
        //viewModel.setLoginParameters(this, userInput.text.toString(), orgInput.text.toString(), tokenInput.text.toString())
        startActivity(Intent(this, ProfileActivity::class.java))
    }

    private fun startAnimation() {
        sdsImageView.animate().apply {
            x(50f)
            y(100f)
            duration = 1000
        }.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                afterAnimationView.visibility = VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }
        })


        loginButton.setOnClickListener {
            var userName: String = userNameEditText.text.toString()
            var password = passwordEditText.text.toString()
            var userID = userIDEditText.text.toString() // encrypt(password.text.toString())

            viewModel.init(userName, password, userID)

            /*
            createNotificationChannel()
            // AlarmNotificationReceiver().onReceive(this,Intent())
            startAlarm(true, true)

*/
            /*
            var CHANNEL_ID = "ch"
            var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.sds)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(0, builder.build())
            }

            */

            // startAlarm(true,true)

           val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)

        }

    }


    fun encrypt(pw: String): String {
        val ENCRYPTION_KEY = "POLICARPO É GAY!"
        val input = pw.toString().toByteArray((charset("utf-8")))
        val md = MessageDigest.getInstance("MD5")
        val thedigest = md.digest(ENCRYPTION_KEY.toByteArray((charset("utf-8"))))
        val skc = SecretKeySpec(thedigest, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, skc)

        val cipherText = ByteArray(cipher.getOutputSize(input.size))
        var ctLength = cipher.update(input, 0, input.size, cipherText, 0)
        ctLength += cipher.doFinal(cipherText, ctLength)

        val query = Base64.encodeToString(cipherText, Base64.DEFAULT)
        Log.i("LoginActivity", query)
        return query
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


    private fun startAlarm(isNotification: Boolean, isRepeat: Boolean) {
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val myIntent: Intent
        val pendingIntent: PendingIntent
        val calendar: Calendar
        val alarmStartTime: Calendar
        var now: Calendar

        /*
        // SET TIME HERE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar =  Calendar.getInstance(TimeZone.GMT_ZONE)
        } else {
            TODO("VERSION.SDK_INT < N")
        }

        calendar.set(Calendar.HOUR_OF_DAY, 10)
        calendar.set(Calendar.MINUTE, 52)

*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            alarmStartTime = Calendar.getInstance();
            now = Calendar.getInstance();
            alarmStartTime.set(Calendar.HOUR_OF_DAY, 11);
            alarmStartTime.set(Calendar.MINUTE, 45);
            alarmStartTime.set(Calendar.SECOND, 0);

            if (now.after(alarmStartTime)) {
                Log.d("Hey", "Added a day");
                alarmStartTime.add(Calendar.DATE, 1);

            }
            myIntent = Intent(this@LoginActivity, AlarmNotificationReceiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0)


            if (!isRepeat)
                manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 3000, pendingIntent)
            else
                manager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    alarmStartTime.getTimeInMillis(),
                    //calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )

        } else {
        }
    }
/*
        myIntent = Intent(this@LoginActivity, AlarmNotificationReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0)


        if (!isRepeat)
            manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 3000, pendingIntent)
        else
            manager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                alarmStartTime.getTimeInMillis(),
                //calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
            */
}

