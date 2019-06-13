package com.isel.ps.sds.view.quiz.fragments


import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.isel.ps.sds.R
import com.isel.ps.sds.view.quiz.data.Question
import kotlinx.android.synthetic.main.fragment_time_ans.*
import kotlinx.android.synthetic.main.fragment_time_ans.question





class TimeAnsFragment(var quest: Question) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_ans, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        question.text=quest.question
        if(!quest.userAnswer.finalAnswer.equals("")){
            var time = quest.userAnswer.finalAnswer.split(":")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePickerStart.hour = time[0].toInt()
                timePickerStart.minute = time[1].toInt()
            } else {
                timePickerStart.currentHour = time[0].toInt()
                timePickerStart.currentMinute = time[1].toInt()
            }

        }
        timePickerStart.setIs24HourView(true);
    }

    override fun onPause() {
        super.onPause()
        var hour : String
        var minute :String
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           hour = timePickerStart.hour.toString()
             minute =timePickerStart.minute.toString()
        } else {
             hour =  timePickerStart.currentHour.toString()
             minute = timePickerStart.currentMinute.toString()
        }


        var time = hour+":"+minute
        quest.userAnswer.finalAnswer = time
    }

}
