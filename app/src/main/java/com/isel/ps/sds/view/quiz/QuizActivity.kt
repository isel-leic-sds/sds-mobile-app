package com.isel.ps.sds.view.quiz

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.quiz.fragments.RadioAnsFragment
import kotlinx.android.synthetic.main.fragment_radio_ans.*


class QuizActivity : BaseActivity<QuizViewModel>(),RadioAnsFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        var radio = radioButton.text
    }

    override fun defineViewModel(): Class<QuizViewModel> = QuizViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_loading

    override fun doOnCreate(savedInstanceState: Bundle?) {
        viewModel.init()



        viewModel.getQuiz()?.observe(this, Observer { quiz ->

            var quest = Question("id1","questãoooooo",Answer("coco","xixi"))
            supportFragmentManager.beginTransaction().replace(R.id.frame_question,RadioAnsFragment()).commit()
           /*
            var transaction = mang.beginTransaction()

            // Replace the fragment on container
            transaction.
            transaction.addToBackStack(null)

            // Finishing the transition
            transaction.commit()
            */
/*
            var questsList = quiz.listQuestions
            var quest = questsList.first

            question1.text=quest.question

            var ans = quest.possibleAnswers
            radioButton.text=ans.option1
            radioButton2.text=ans.option2
            setContentView(R.layout.fragment_radio_answer)
*/
        })

        print("Test")

    }
}