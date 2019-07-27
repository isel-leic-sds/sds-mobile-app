package com.isel.ps.sds.view.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.repository
import com.isel.ps.sds.requestQueue
import com.isel.ps.sds.view.BaseViewModel
import com.isel.ps.sds.view.login.LoginFactory.Login

class LoginViewModel(private val app : Application) : BaseViewModel(app) {
    private var loginState = MutableLiveData<Boolean>()
    private var errorMessage = MutableLiveData<String>()

    fun tryLogin(login: Login) = app.repository.tryLogin(
        app.requestQueue,
        login,
        {
            loginState.value = true
            app.repository.setLoginParameters(login.sdsID, login.password)
        },
        {
            loginState.value = false
            errorMessage.value = it
        }
    )

    fun isLoggedIn(): Boolean = app.repository.isLoggedIn()

    fun getLoginState(): LiveData<Boolean> = loginState
    fun getErrorMessage(): LiveData<String> = errorMessage
}

