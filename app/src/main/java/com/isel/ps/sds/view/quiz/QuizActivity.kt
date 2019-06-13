package com.isel.ps.sds.view.quiz

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.isel.ps.sds.FragmentFactory
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.menu.MenuActivity
import kotlinx.android.synthetic.main.activity_loading.*
import java.io.File
import java.time.LocalDateTime


class QuizActivity : BaseActivity<QuizViewModel>(){

    val jackson = jacksonObjectMapper()
    override fun defineViewModel(): Class<QuizViewModel> = QuizViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_loading

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doOnCreate(savedInstanceState: Bundle?) {
        viewModel.init()
        submit_quiz_button.visibility = View.GONE

        viewModel.getQuiz()?.observe(this, Observer { quiz ->
            var questIdx = viewModel.getCurrentIdx()
            var quest = quiz.questions[questIdx]
            var frag = FragmentFactory().getFragment(quest)

            progressBar.visibility= View.GONE
            prev_question_button.visibility = View.GONE
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, frag).commit()
        })

        next_question_button.setOnClickListener(View.OnClickListener {
            prev_question_button.visibility = View.VISIBLE
            var frag = getCurrentFragment()
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, frag).commit()
        })

        prev_question_button.setOnClickListener {
            var frag = getCurrentFragment(false)
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, frag).commit()
        }

        submit_quiz_button.setOnClickListener {
            jackson.writerWithDefaultPrettyPrinter().
                writeValue(
                    File(getFilesDir().getAbsolutePath(),
                        "quiz_"+ LocalDateTime.now().toString()+".json"), viewModel.getQuiz())
            //TODO enviar as respostas através do voley
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)


        }
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