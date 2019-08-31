package com.isel.ps.sds.view.clinicalHistory

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.isel.ps.sds.FragmentAnsFactory
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistory
import kotlinx.android.synthetic.main.activity_loading.*

class ClinicalHistoryActivity : BaseActivity<ClinicalHistoryViewModel>() {
    override fun defineViewModel(): Class<ClinicalHistoryViewModel> =
        ClinicalHistoryViewModel::class.java

    override fun layoutToInflate(): Int = R.layout.activity_loading//R.layout.activity_clinical_history

    override fun doOnCreate(savedInstanceState: Bundle?) {
        submit_quiz_button.visibility = View.GONE

        viewModel.clinicalHistory.observe(this, Observer { clinicalHistory ->
            progressBar.visibility= View.GONE
            prev_question_button.visibility = View.GONE
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, getFragmentByClinicalHist(clinicalHistory)).commit()
        })

        next_question_button.setOnClickListener {
            prev_question_button.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, getCurrentFragment()).commit()
        }

        prev_question_button.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.frame_question, getCurrentFragment(false)).commit()
        }

    }

    private fun getFragmentByClinicalHist(ch: ClinicalHistory): Fragment {
        val idx = viewModel.getCurrentIdx()
        val clinicalHist = ch.ch[idx]    // questions[questIdx]
        return FragmentAnsFactory().getClinicalHistoryFragment(clinicalHist)
    }

    // If next = true get the next question, on false get the previous question
    private fun getCurrentFragment(next: Boolean = true): Fragment {
        val idx: Int = if (next) viewModel.nextQuestionNumber() else viewModel.prevQuestionNumber()
        val ch = viewModel.clinicalHistory.value!!.ch [idx]
        return FragmentAnsFactory().getClinicalHistoryFragment(ch)
    }

}