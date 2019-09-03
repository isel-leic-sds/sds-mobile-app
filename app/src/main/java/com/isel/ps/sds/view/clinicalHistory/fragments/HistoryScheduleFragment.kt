package com.isel.ps.sds.view.clinicalHistory.fragments

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.format.DateFormat
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
import java.sql.Date
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

        var faseDay = ArrayList<Int>(4)
        faseDay.add(0,0)
        faseDay.add(1,0)
        faseDay.add(2,0)
        faseDay.add(3,0)

        funcao(faseDay)
        barEntries.add(BarEntry(0F, faseDay[0].toFloat()))
        barEntries.add(BarEntry(1F, faseDay[1].toFloat()))
        barEntries.add(BarEntry(2F, faseDay[2].toFloat()))
        barEntries.add(BarEntry(3F, faseDay[3].toFloat()))

        var barDataSet = BarDataSet(barEntries,clinicalHistory.question)

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


    fun funcao(dayFases: ArrayList<Int>){
        var answers = clinicalHistory.answers
        var answer = ""
        var size = 0
        val zeroHours = SimpleDateFormat("HH:mm").parse("00:00")
        val sevenHours = SimpleDateFormat("HH:mm").parse("07:00")
        val twelveHours = SimpleDateFormat("HH:mm").parse("12:00")
        val twentyHours = SimpleDateFormat("HH:mm").parse("20:00")
        val twentyFourHours = SimpleDateFormat("HH:mm").parse("23:59")
        for (i: Int in 0 until answers.size){
           answer = answers[i].answer
            size = answers[i].userAnswer.size
           var date = SimpleDateFormat("HH:mm").parse(answer)
            if(date>=zeroHours && date < sevenHours){
                dayFases[0] = dayFases[0] + size
            }
            if(date>=sevenHours && date < twelveHours){
                dayFases[1] = dayFases[1] + size
            }
            if(date>=twelveHours && date < twentyHours){
                dayFases[2] = dayFases[2] + size
            }

            if(date>=twentyHours && date < twentyFourHours){
                dayFases[3] = dayFases[3] + size
            }

        }
        dayFases
    }


}
