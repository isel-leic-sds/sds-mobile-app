package com.isel.ps.sds.view.quiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.isel.ps.sds.R
import com.isel.ps.sds.view.quiz.data.Question
import kotlinx.android.synthetic.main.fragment_seek_ans.*
import kotlinx.android.synthetic.main.fragment_seek_ans.view.*

class SeekAnsFragment(var quest: Question) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root= inflater.inflate(R.layout.fragment_seek_ans, container, false)
        var progessTextView = root.progress_textView
        root.seekBar_widget.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Write code to perform some action when progress is changed.
                progessTextView.text=seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.

            }
        })


        return root
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
