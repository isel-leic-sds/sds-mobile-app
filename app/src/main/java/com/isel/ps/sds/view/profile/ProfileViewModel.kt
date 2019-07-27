package com.isel.ps.sds.view.profile

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.repository
import com.isel.ps.sds.requestQueue
import com.isel.ps.sds.view.BaseViewModel

class ProfileViewModel(private val app: Application) : BaseViewModel(app) {

    private val person = MutableLiveData<Person>()
    fun providePerson(): LiveData<Person> = person

    fun getPatientData(context: Context) = app.repository.getPatientData(
        app.requestQueue,
        { newPerson -> person.value = newPerson },
        { msg -> Toast.makeText(context, "Load person data failed: $msg", Toast.LENGTH_LONG).show() }
    )
}
