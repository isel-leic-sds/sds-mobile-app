package com.isel.ps.sds.view.menu

import android.os.Bundle
import android.content.Intent
import com.example.myapplication.LoginViewModel
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.notification.NotificationActivity
import com.isel.ps.sds.view.profile.ProfileActivity
import com.isel.ps.sds.view.quiz.QuizActivity
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : BaseActivity<LoginViewModel>() {

    override fun defineViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun layoutToInflate(): Int = com.isel.ps.sds.R.layout.activity_menu

    override fun doOnCreate(savedInstanceState: Bundle?) {

        profileImageView.setOnClickListener {
            val i: Intent = Intent(this, ProfileActivity::class.java)
            startActivity(i)
        }

        checkupImageView.setOnClickListener {
            val i: Intent = Intent(this, QuizActivity::class.java)
            startActivity(i)
        }

        notificationImageView.setOnClickListener {
            val i: Intent = Intent(this, NotificationActivity::class.java)
            startActivity(i)
        }

    }
}