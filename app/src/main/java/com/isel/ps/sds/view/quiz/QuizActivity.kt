package com.isel.ps.sds.view.quiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.isel.ps.sds.FragmentFactory
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.menu.MenuActivity
import kotlinx.android.synthetic.main.activity_loading.*


class QuizActivity : BaseActivity<QuizViewModel>(){


    override fun defineViewModel(): Class<QuizViewModel> = QuizViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_loading

    override fun doOnCreate(savedInstanceState: Bundle?) {
        viewModel.init()
        submit_quiz_button.visibility = View.GONE

        viewModel.getQuiz()?.observe(this, Observer { quiz ->
            var questIdx = viewModel.getCurrentIdx()
            var quest = quiz.questions[questIdx]
            var frag = FragmentFactory().getFragment(quest)
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, frag).commit()
        })

        next_question_button.setOnClickListener(View.OnClickListener {

            var frag = getCurrentFragment()
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, frag).commit()
        })

        prev_question_button.setOnClickListener({
            var frag = getCurrentFragment(false)
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, frag).commit()
        })

        submit_quiz_button.setOnClickListener({
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            //Todo Enviar por volley as respostas

        })
    }
    // If next = true get the next question, on false get the previous question
    fun getCurrentFragment(next:Boolean = true): Fragment {
        var questIdx:Int

        if(next) questIdx = viewModel.nextQuestionNumber()
        else    questIdx = viewModel.prevQuestionNumber()

        var quest = viewModel.getQuiz()?.value!!.questions[questIdx]
        var frag = FragmentFactory().getFragment(quest)
        return frag
    }
}