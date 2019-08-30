package com.isel.ps.sds.view.clinicalHistory

import android.app.Application
import androidx.lifecycle.LiveData
import com.isel.ps.sds.view.BaseViewModel
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistory

class ClinicalHistoryViewModel(private val app : Application) : BaseViewModel(app) {
    val clinicalHistory: LiveData<ClinicalHistory> =
        ClinicalHistoryRepository.loadClinicalHistoryData(app.applicationContext)
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
}