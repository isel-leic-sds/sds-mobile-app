package com.isel.ps.sds.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    private val isLoading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<Exception>()

    fun provideLoadingState(): LiveData<Boolean> = isLoading
    fun provideErrors(): LiveData<Exception> = error

    fun isLoading(value: Boolean) = isLoading.postValue(value)

    fun error(value: Exception) {
        error.postValue(value)
        isLoading(false)
    }
}
