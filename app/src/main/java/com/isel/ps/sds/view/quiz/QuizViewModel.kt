package com.isel.ps.sds.view.quiz

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.myapplication.Login

import com.isel.ps.sds.view.BaseViewModel

class QuizViewModel(private val app : Application) : BaseViewModel(app) {
    private var login: LiveData<Login>? = null
    //private var loginRepo: LoginRepository


    fun init(userName: String, password: String, userId: String) {
        if (this.login != null ) {

            return
        }
        Login(userName,userId,password)
        // try {
        this.login =  null
        //LoginRepository.tryLogin(userName,userId)
        // }
    }

}
