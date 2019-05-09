package com.isel.ps.sds.view.quiz

import android.os.Bundle
import com.example.myapplication.LoginViewModel
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : BaseActivity<QuizViewModel>()  {
    override fun defineViewModel(): Class<QuizViewModel> = QuizViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_quiz
    override fun doOnCreate(savedInstanceState: Bundle?) {
    //question1.setText("OLA tudo bem?")
    }
}