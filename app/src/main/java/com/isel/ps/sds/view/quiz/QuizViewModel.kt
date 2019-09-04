package com.isel.ps.sds.view.quiz

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.repository
import com.isel.ps.sds.requestQueue
import com.isel.ps.sds.view.BaseViewModel
import com.isel.ps.sds.view.quiz.data.Quiz

class QuizViewModel(private val app : Application) : BaseViewModel(app) {
    private var questIdx: Int = 0
    val quiz = MutableLiveData<Quiz>()

    fun getQuiz() {
        app.repository.getQuiz(
            { newQuiz -> quiz.value = newQuiz },
            { newQuiz -> app.repository.submitQuiz(newQuiz) },
            { err -> setError(err) }
        )
    }

    fun getCurrentIdx():Int{
        return questIdx
    }

    fun nextQuestionNumber():Int {
        questIdx = getCurrentIdx() + 1
        return questIdx
    }

    fun prevQuestionNumber():Int {
        questIdx = if (getCurrentIdx() - 1 < 0) 0 else getCurrentIdx() - 1
        return questIdx
    }

    fun submitQuiz() = app.repository.submitDailyQuiz(
        app.requestQueue,
        quiz.value!!,
        { isLoading.value = true },
        { err ->
            isLoading.value = false
            setError(err)
        })
}
