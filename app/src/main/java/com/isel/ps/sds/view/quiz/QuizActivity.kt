package com.isel.ps.sds.view.quiz

import android.os.Bundle
import androidx.lifecycle.Observer
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity



class QuizActivity : BaseActivity<QuizViewModel>() {
    override fun defineViewModel(): Class<QuizViewModel> = QuizViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_loading

    override fun doOnCreate(savedInstanceState: Bundle?) {
        viewModel.init()



        viewModel.getQuiz()?.observe(this, Observer { quiz ->

/*
            var questsList = quiz.listQuestions
            var quest = questsList.first

            question1.text=quest.question

            var ans = quest.possibleAnswers
            radioButton.text=ans.option1
            radioButton2.text=ans.option2
            setContentView(R.layout.activity_radio_answer)
*/
        })

        print("Test")

    }
}