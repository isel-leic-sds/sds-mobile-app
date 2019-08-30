package com.isel.ps.sds.view.clinicalHistory.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.isel.ps.sds.R
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistory
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistoryData
import kotlinx.android.synthetic.main.fragment_history_seek.*

class HistorySeekFragment(var clinicalHistory: ClinicalHistoryData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_seek, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        var lineChart = lineChart
        lineChart.isDragEnabled=true
        lineChart.setScaleEnabled(false)

        var yValues = ArrayList<Entry>(7)

        var answers = clinicalHistory.answers

        for (i: Int in 0..(answers.size - 1)) {
            var userAnswer = answers[i].userAnswer
            for (j: Int in 0..(userAnswer.size - 1)) {
                yValues.add(Entry(userAnswer[j].toFloat(), answers[i].answer.toFloat()))
            }
        }


        var set1 = LineDataSet(yValues,clinicalHistory.question)
        set1.fillAlpha=150
        set1.lineWidth=3F
        var dataSets = ArrayList<LineDataSet>(10)
        dataSets.add(set1)
        var data = LineData(dataSets as List<ILineDataSet>?)
        lineChart.data=data
    }

}