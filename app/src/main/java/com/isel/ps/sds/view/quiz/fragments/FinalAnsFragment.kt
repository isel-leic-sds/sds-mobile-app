package com.isel.ps.sds.view.quiz.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.isel.ps.sds.R
import com.isel.ps.sds.view.menu.MenuActivity
import kotlinx.android.synthetic.main.activity_loading.*


class FinalAnsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_final_ans, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onStart() {
        super.onStart()
        activity?.next_question_button?.visibility = View.GONE
        activity?.submit_quiz_button?.visibility=View.VISIBLE
    }


    override fun onPause() {
        super.onPause()
        activity?.next_question_button?.visibility = View.VISIBLE
        activity?.submit_quiz_button?.visibility=View.GONE
    }
}
