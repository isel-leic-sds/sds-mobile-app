package com.isel.ps.sds.view.clinicalHistory

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.repository
import com.isel.ps.sds.requestQueue
import com.isel.ps.sds.view.BaseViewModel
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistory

class ClinicalHistoryViewModel(private val app : Application) : BaseViewModel(app) {
    val clinicalHistory = MutableLiveData<ClinicalHistory>()
    private var idx: Int = 0

    fun getCurrentIdx(): Int {
        return idx
    }

    fun nextQuestionNumber(): Int {
        idx = getCurrentIdx() + 1
        return idx
    }

    fun prevQuestionNumber(): Int {
        idx = if (getCurrentIdx() - 1 < 0) 0 else getCurrentIdx() - 1
        return idx
    }

    fun getClinicalHistory() {
        app.repository.getQuizResults(
            app.requestQueue,
            { newClinicalHistory -> clinicalHistory.value = newClinicalHistory },
            { err -> error(err) }
        )
    }
}