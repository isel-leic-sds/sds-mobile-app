package com.isel.ps.sds

import android.app.Application
import androidx.multidex.MultiDexApplication

class DigitalHealthSystemApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
//        DhsService.init(this)
    }
    val repository by lazy {
//        DhsRepository (
//
//        )
    }
}

val Application.repository
    get() = (this as DigitalHealthSystemApplication).repository