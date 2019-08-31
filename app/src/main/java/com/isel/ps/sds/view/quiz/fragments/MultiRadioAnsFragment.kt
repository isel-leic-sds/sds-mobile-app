package com.isel.ps.sds.view.quiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.isel.ps.sds.R
import com.isel.ps.sds.view.quiz.data.Question
import kotlinx.android.synthetic.main.fragment_radio_ans.frag_question
import kotlinx.android.synthetic.main.fragment_radio_ans.optionButton1
import kotlinx.android.synthetic.main.fragment_radio_ans.optionButton2

class MultiRadioAnsFragment (var quest: Question) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_radio_ans, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        frag_question.text = quest.question
        optionButton1.text = quest.answerOptions.option1
        optionButton2.text = quest.answerOptions.option2
//        optionButton3.text = quest.answerOptions.option3
//        optionButton3.text = quest.answerOptions.option4
        var userAnswer = quest.userAnswer.finalAnswer
        if (!userAnswer.equals("")) {
            if (userAnswer.equals(quest.answerOptions.option1))
                optionButton1.isChecked = true
            else optionButton2.isChecked = true
        }
    }

    override fun onPause() {
        super.onPause()
        if (optionButton1.isChecked) quest.userAnswer.finalAnswer = optionButton1.text.toString()
        if (optionButton2.isChecked) quest.userAnswer.finalAnswer = optionButton2.text.toString()
    }
}