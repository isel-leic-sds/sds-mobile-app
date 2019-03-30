package com.example.myapplication


import androidx.lifecycle.MutableLiveData

object LoginRepository {
    var data: MutableLiveData<Login>? = null

    fun tryLogin(userName: String, userID: Int, password:String ): MutableLiveData<Login>? {

        return data;
    }
}
