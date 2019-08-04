package com.isel.ps.sds.view.quiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.isel.ps.sds.R
import com.isel.ps.sds.view.quiz.data.Question
import kotlinx.android.synthetic.main.fragment_seek_ans.*

class SeekAnsFragment(var quest: Question) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seek_ans, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        frag_question.text = quest.question
        seekBar_widget.max = quest.answerOptions.option1.toInt()-1
        if(!quest.userAnswer.finalAnswer.equals("")) {
            var progress = quest.userAnswer.finalAnswer
            seekBar_widget.progress = progress.toInt()
            progress_textView.text = progress
        }
    }

    override fun onPause() {
        super.onPause()
        quest.userAnswer.finalAnswer = seekBar_widget.progress.toString()
    }
}
