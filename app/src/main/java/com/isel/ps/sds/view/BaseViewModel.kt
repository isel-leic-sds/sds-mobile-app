package com.isel.ps.sds.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    val isLoading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<Exception>()

    fun provideLoadingState(): LiveData<Boolean> = isLoading
    fun provideErrors(): LiveData<Exception> = error

    fun error(value: Exception) {
        error.value = value
        isLoading.value = false
    }
}
