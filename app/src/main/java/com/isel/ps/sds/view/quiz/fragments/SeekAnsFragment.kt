package com.isel.ps.sds.view.quiz.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.isel.ps.sds.R
import com.isel.ps.sds.view.quiz.data.Question
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.fragment_seek_ans.*


class SeekAnsFragment(var quest: Question) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seek_ans, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        question.text = quest.question
        seekBar_widget.max = quest.answerOptions.option1.toInt()-1
        if(!quest.userAnswer.finalAnswer.equals(""))
            seekBar_widget.progress = quest.userAnswer.finalAnswer.toInt()
    }

    override fun onPause() {
        super.onPause()
        quest.userAnswer.finalAnswer = seekBar_widget.progress.toString()
    }
}
