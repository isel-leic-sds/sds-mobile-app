package com.isel.ps.sds.view.quiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.isel.ps.sds.FragmentAnsFactory
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.menu.MenuActivity
import com.isel.ps.sds.view.quiz.data.Quiz
import kotlinx.android.synthetic.main.activity_loading.*


class QuizActivity : BaseActivity<QuizViewModel>(){

    override fun defineViewModel(): Class<QuizViewModel> = QuizViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_loading

    override fun doOnCreate(savedInstanceState: Bundle?) {
        submit_quiz_button.visibility = View.GONE

        viewModel.quiz.observe(this, Observer { quiz ->
            progressBar.visibility= View.GONE
            prev_question_button.visibility = View.GONE
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, getFragmentByQuiz(quiz)).commit()
        })

        viewModel.getQuiz()

        next_question_button.setOnClickListener {
            prev_question_button.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, getCurrentFragment()).commit()
        }

        prev_question_button.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, getCurrentFragment(false)).commit()
        }

        submit_quiz_button.setOnClickListener {
            submit_quiz_button.isEnabled = false
            viewModel.submitQuiz()
        }

        viewModel.isLoading.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, getString(R.string.submitQuiz), Toast.LENGTH_LONG).show()
                renderMenuActivity()
            } else {
                submit_quiz_button.isEnabled = true
            }
        })

        viewModel.error.observe(
            this,
            Observer {
                Toast.makeText(
                    this,
                    getString(R.string.quizFailedMessage),
                    Toast.LENGTH_LONG
                ).show()
            })
    }

    private fun getFragmentByQuiz(quiz: Quiz): Fragment {
        val questIdx = viewModel.getCurrentIdx()
        val quest = quiz.questions[questIdx]
        return FragmentAnsFactory().getFragment(quest)
    }

    // If next = true get the next question, on false get the previous question
    private fun getCurrentFragment(next: Boolean = true): Fragment {
        val questIdx: Int = if (next) viewModel.nextQuestionNumber() else viewModel.prevQuestionNumber()
        val quest = viewModel.quiz.value!!.questions[questIdx]
        return FragmentAnsFactory().getFragment(quest)
    }

    private fun renderMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}