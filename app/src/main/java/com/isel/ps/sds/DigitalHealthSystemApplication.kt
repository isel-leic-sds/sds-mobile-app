package com.isel.ps.sds

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.android.volley.toolbox.Volley

class DigitalHealthSystemApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
//        DhsService.init(this)
        VolleyService.init(this)
    }

    val repository by lazy { DhsRepository() }

    val requestQueue by lazy { Volley.newRequestQueue(this) }
}

val Application.repository
    get() = (this as DigitalHealthSystemApplication).repository

val Application.requestQueue
    get() = (this as DigitalHealthSystemApplication).requestQueue