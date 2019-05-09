package com.isel.ps.sds.view.menu

import android.app.Activity
import android.os.Bundle
import android.R
import android.content.Intent
import android.view.View
import com.example.myapplication.LoginViewModel
import com.isel.ps.sds.view.BaseActivity
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

    }
}