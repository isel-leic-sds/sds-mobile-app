package com.isel.ps.sds.view.menu

import android.content.Intent
import android.os.Bundle
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.clinicalHistory.ClinicalHistoryActivity
import com.isel.ps.sds.view.login.LoginViewModel
import com.isel.ps.sds.view.notification.NotificationActivity
import com.isel.ps.sds.view.profile.ProfileActivity
import com.isel.ps.sds.view.quiz.QuizActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : BaseActivity<LoginViewModel>() {

    override fun defineViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun layoutToInflate(): Int = com.isel.ps.sds.R.layout.activity_menu

    override fun doOnCreate(savedInstanceState: Bundle?) {
        profile_img.setOnClickListener {
            val profileActivity = Intent(this, ProfileActivity::class.java)
            startActivity(profileActivity)
        }

        daily_img.setOnClickListener {
            val quizActivity = Intent(this, QuizActivity::class.java)
            startActivity(quizActivity)
        }

        alert_settings_img.setOnClickListener {
            val notificationActivity = Intent(this, NotificationActivity::class.java)
            startActivity(notificationActivity)
        }

        clinical_history_img.setOnClickListener {
            val clinicalActivity = Intent(this, ClinicalHistoryActivity::class.java)
            startActivity(clinicalActivity)
        }
    }


}