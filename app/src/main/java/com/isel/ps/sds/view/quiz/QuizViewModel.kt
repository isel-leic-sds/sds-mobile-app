package com.isel.ps.sds.view.quiz

import android.app.Application
import androidx.lifecycle.LiveData
import com.isel.ps.sds.repository
import com.isel.ps.sds.requestQueue
import com.isel.ps.sds.view.BaseViewModel
import com.isel.ps.sds.view.quiz.data.Quiz

class QuizViewModel(private val app : Application) : BaseViewModel(app) {
    val quiz: LiveData<Quiz> = QuizRepository.loadQuestionData(app.applicationContext)
    private var questIdx: Int = 0

    fun getCurrentIdx():Int{
        return questIdx
    }

    fun nextQuestionNumber():Int {
        questIdx = getCurrentIdx() + 1
        return questIdx
    }

    fun prevQuestionNumber():Int {
        if (getCurrentIdx() - 1 < 0) 0 else getCurrentIdx() - 1
        questIdx = if (getCurrentIdx() - 1 < 0) 0 else getCurrentIdx() - 1
        return questIdx
    }

    fun submitQuiz() = app.repository.submitQuiz(
        app.requestQueue,
        quiz.value!!,
        { isLoading.value = true },
        { err -> error(err) })
}
