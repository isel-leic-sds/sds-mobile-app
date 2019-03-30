package com.example.myapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.isel.ps.sds.view.BaseViewModel


private val LoginVM = "LoginVM"
class LoginViewModel(private val app : Application) : BaseViewModel(app) {
    private var login: LiveData<Login>? = null
    //private var loginRepo: LoginRepository


    fun init(userName: String, password: String, userId: String) {
        if (this.login != null ) {
            Log.i(LoginVM, "Init ViewModel")
            return
        }
        Login(userName,userId,password)
       // try {
            this.login =  null
            //LoginRepository.tryLogin(userName,userId)
       // }
    }


    fun getLogin(): LiveData<Login>? {
        return this.login
    }
}

