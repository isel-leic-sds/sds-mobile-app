package com.isel.ps.sds.view.clinicalHistory.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.isel.ps.sds.R
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistoryData
import kotlinx.android.synthetic.main.fragment_history_binary.*

class HistoryBinaryFragment (var clinicalHistory: ClinicalHistoryData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_binary, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pieChart = pieChart
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5F, 10F, 5F, 5F)
        pieChart.dragDecelerationFrictionCoef = 0.99F
        pieChart.isDrawHoleEnabled = false
        pieChart.setHoleColor(Color.WHITE)
        pieChart.transparentCircleRadius = 61F

        val yValues = ArrayList<PieEntry>()


        yValues.add(
            PieEntry(
                clinicalHistory.answers[0].userAnswer.size.toFloat(),
                clinicalHistory.answers[0].answer
            )
        )
        yValues.add(
            PieEntry(
                clinicalHistory.answers[1].userAnswer.size.toFloat(),
                clinicalHistory.answers[1].answer
            )
        )

        val dataSet = PieDataSet(yValues, clinicalHistory.question)
        dataSet.sliceSpace = 3F
        dataSet.selectionShift = 5F
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()

        val data = PieData(dataSet)
        data.setValueTextSize(10F)
        data.setValueTextColor(Color.YELLOW)
        pieChart.data = data
    }
}