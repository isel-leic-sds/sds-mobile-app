package com.isel.ps.sds.view.login

import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.view.login.LoginFactory.Login

object LoginRepository {
    var data: MutableLiveData<Login>? = null

    fun tryLogin(userName: String, userID: Int, password:String ): MutableLiveData<Login>? {

        return data;
    }
}
