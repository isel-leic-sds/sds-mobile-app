package com.isel.ps.sds.view.quiz

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.myapplication.Login

import com.isel.ps.sds.view.BaseViewModel

class QuizViewModel(private val app : Application) : BaseViewModel(app) {
    private var quiz: LiveData<Quiz>? = null



    fun init() {
        if (this.quiz != null ) {

            return
        }
        this.quiz = QuizRepository.loadQuestionData(app.applicationContext)

    }

    fun getQuiz(): LiveData<Quiz>? {
        return this.quiz
    }

}
