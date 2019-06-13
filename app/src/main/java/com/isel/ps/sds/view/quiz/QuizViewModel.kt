package com.isel.ps.sds.view.quiz

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.view.BaseViewModel
import com.isel.ps.sds.view.quiz.data.Quiz

class QuizViewModel(private val app : Application) : BaseViewModel(app) {
    private var quiz: LiveData<Quiz>? = null
    private var questIdx: MutableLiveData<Int> = MutableLiveData()

    fun init() {
        if (this.quiz != null ) {

            return
        }
        this.questIdx.value=0
        this.quiz = QuizRepository.loadQuestionData(app.applicationContext)


    }

    fun getQuiz(): LiveData<Quiz>? {
        return this.quiz
    }


    fun getCurrentIdx():Int{
        return questIdx.value!!
    }

    fun nextQuestionNumber():Int {
        var idx = getCurrentIdx()+1
        questIdx.value=idx
        return questIdx.value!!

    }

    fun prevQuestionNumber():Int {
        var idx = getCurrentIdx()-1
        if (idx < 0) idx=0
        questIdx.value=idx
        return questIdx.value!!
    }
}
