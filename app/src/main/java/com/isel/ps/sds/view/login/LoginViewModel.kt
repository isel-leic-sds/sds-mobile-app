package com.isel.ps.sds.view.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.repository
import com.isel.ps.sds.requestQueue
import com.isel.ps.sds.view.BaseViewModel
import com.isel.ps.sds.view.login.LoginFactory.Login

class LoginViewModel(private val app : Application) : BaseViewModel(app) {
    private val loginState = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()

    fun tryLogin(login: Login) {
        app.repository.tryLogin(
            app.requestQueue,
            login,
            {
                loginState.value = true
                app.repository.setLoginParameters(login.sdsID, login.password)
                loadPatientData()
            },
            {
                loginState.value = false
                errorMessage.value = it
            }
        )
    }

    fun isLoggedIn(): Boolean = app.repository.isLoggedIn()

    fun getLoginState(): LiveData<Boolean> = loginState
    fun getErrorMessage(): LiveData<String> = errorMessage

    private fun loadPatientData() {
        app.repository.getPatientData(
            app.requestQueue,
            { newPerson ->
                app.repository.setPatientParameters(newPerson)
                app.repository.submitPerson(newPerson)
                loadQuizData()
            },
            { msg -> errorMessage.value = msg }
        )
    }

    private fun loadQuizData() {
        app.repository.getPatientQuiz(
            app.requestQueue,
            { newQuiz -> app.repository.submitQuiz(newQuiz) },
            { msg -> errorMessage.value = msg }
        )
    }
}
