package com.isel.ps.sds.view.clinicalHistory

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.R
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistory
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistoryAnswer
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistoryData
import org.json.JSONObject
import java.io.InputStream

object ClinicalHistoryRepository {

    fun loadClinicalHistoryData(context: Context): MutableLiveData<ClinicalHistory> {
        var data: MutableLiveData<ClinicalHistory> = MutableLiveData();


        val inputStream: InputStream = context.resources.openRawResource(R.raw.history_clinical_data)
        val inputString = inputStream.bufferedReader().use { it.readText() }
        val obj = JSONObject(inputString)
        val clinicalHistory = obj.getJSONArray("clinicalHistory") //Array ClinicalHistory

        val questList: ArrayList<ClinicalHistory> = ArrayList()
        val chDataList: ArrayList<ClinicalHistoryData> = ArrayList()

        var clinicalHistoryData : ClinicalHistoryData = ClinicalHistoryData()
        for (i: Int in 0..(clinicalHistory.length() - 1)) {
            val chData = clinicalHistory.getJSONObject(i)
            val type = chData.getString("type")
            val quest = chData.getString("question")

            val answers = chData.getJSONArray("answers")
            clinicalHistoryData = ClinicalHistoryData()
            val answersList: ArrayList<ClinicalHistoryAnswer> = ArrayList()
            var chAnswer: ClinicalHistoryAnswer
            for (i: Int in 0..(answers.length() - 1)) {
                val answer = answers.getJSONObject(i)

                var ans = answer.getString("answer")
                var userAnswer = answer.getJSONArray("userAnswer")
                val userAnswers: ArrayList<String> = ArrayList()

                for (i: Int in 0..(userAnswer.length() - 1)) {
                    userAnswers.add(userAnswer.get(i).toString())
                }
                chAnswer = ClinicalHistoryAnswer(ans, userAnswers)
                answersList.add(chAnswer)
                clinicalHistoryData = ClinicalHistoryData(type, quest, answersList)
            }

            chDataList.add(clinicalHistoryData)
        }
        chDataList.add(ClinicalHistoryData("Final"))
        data.value=ClinicalHistory(chDataList)
        return data
    }
}