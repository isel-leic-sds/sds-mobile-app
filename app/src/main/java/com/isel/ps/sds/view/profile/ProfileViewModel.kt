package com.isel.ps.sds.view.profile

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.repository
import com.isel.ps.sds.view.BaseViewModel

class ProfileViewModel(private val app: Application) : BaseViewModel(app) {
    val person = MutableLiveData<Person>()

    fun getPerson() {
        app.repository.getPerson(
            { newPerson -> person.value = newPerson },
            { newPerson -> app.repository.submitPerson(newPerson) },
            { err -> error(err) }
        )
    }
}
