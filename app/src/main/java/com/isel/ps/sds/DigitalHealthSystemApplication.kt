package com.isel.ps.sds

import android.app.Application
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class DigitalHealthSystemApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        VolleyService.init(this)
    }

    private val database by lazy {
        Room.databaseBuilder(this, DhsDatabase::class.java, "dhs_db").build()
    }

    val repository by lazy { DhsRepository(this, database.personDao(), database.quizDao()) }

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }
}

val Application.repository
    get() = (this as DigitalHealthSystemApplication).repository

val Application.requestQueue
    get() = (this as DigitalHealthSystemApplication).requestQueue