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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_seek, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val ch: ClinicalHistory // ClinicalHistoryRepository.loadClinicalHistoryData(this).value!!


        var lineChart = lineChart
        lineChart.isDragEnabled=true
        lineChart.setScaleEnabled(false)

        var yValues = ArrayList<Entry>(7)

        yValues.add(Entry(1F,0F))
        yValues.add(Entry(2F,0F))
        yValues.add(Entry(3F,0F))
        yValues.add(Entry(4F,5F))
        yValues.add(Entry(5F,6F))
        yValues.add(Entry(6F,3F))
        yValues.add(Entry(7F,1F))
        yValues.add(Entry(8F,0F))
        yValues.add(Entry(9F,1F))
        yValues.add(Entry(10F,0F))
        yValues.add(Entry(11F,0F))
        yValues.add(Entry(12F,6F))
        yValues.add(Entry(13F,7F))
        yValues.add(Entry(14F,3F))
        yValues.add(Entry(15F,3F))
        yValues.add(Entry(16F,4F))
        yValues.add(Entry(17F,5F))
        yValues.add(Entry(18F,2F))
        yValues.add(Entry(19F,1F))
        yValues.add(Entry(20F,2F))


        var set1 = LineDataSet(yValues,"Data Set 1")
        set1.fillAlpha=150
        set1.lineWidth=3F
        var dataSets = ArrayList<LineDataSet>(10)
        dataSets.add(set1)
        var data = LineData(dataSets as List<ILineDataSet>?)
        lineChart.data=data
    }

}