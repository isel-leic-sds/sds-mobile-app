package com.isel.ps.sds.view.clinicalHistory.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

import com.isel.ps.sds.R
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistoryData
import kotlinx.android.synthetic.main.history_schedule_fragment.*
import java.text.SimpleDateFormat

class HistoryScheduleFragment(var clinicalHistory: ClinicalHistoryData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.history_schedule_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var barCHart = barChart

        barCHart.setDrawBarShadow(false)
        barCHart.setDrawValueAboveBar(true)
        barCHart.setMaxVisibleValueCount(50)
        barCHart.setPinchZoom(false)
        barCHart.setDrawGridBackground(true)

       var  barEntries = ArrayList<BarEntry> ()

        barEntries.add(BarEntry(0F, 5F))
        barEntries.add(BarEntry(1F, 10F))
        barEntries.add(BarEntry(2F, 15F))
       barEntries.add(BarEntry(3F, 1F))

        var barDataSet = BarDataSet(barEntries,"PERGUNTA")

        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()

       var barData = BarData(barDataSet)
        barData.barWidth=0.5F
        barCHart.data=barData

        var dayFases = ArrayList<String>()
        dayFases.add("(0h->6:59h)")
        dayFases.add("(7h->11:59h)")
        dayFases.add("(12h->7:59h)")
        dayFases.add("(20h->23:59h")


        var xAxis=barCHart.xAxis
        xAxis.valueFormatter=MyAxisValueFormatter(dayFases)
//        xAxis.position= XAxis.XAxisPosition.BOTH_SIDED
       xAxis.granularity=1F
 //      xAxis.setCenterAxisLabels(true)
//        xAxis.axisMaximum=1F

    }

    class MyAxisValueFormatter(val dayFases: ArrayList<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return dayFases[value.toInt()]
        }
    }


}
