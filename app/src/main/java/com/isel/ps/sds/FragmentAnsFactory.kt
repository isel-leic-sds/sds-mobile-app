package com.isel.ps.sds

import androidx.fragment.app.Fragment
import com.isel.ps.sds.view.quiz.data.Question
import com.isel.ps.sds.view.quiz.fragments.FinalAnsFragment
import com.isel.ps.sds.view.quiz.fragments.RadioAnsFragment
import com.isel.ps.sds.view.quiz.fragments.SeekAnsFragment
import com.isel.ps.sds.view.quiz.fragments.TimeAnsFragment

class FragmentAnsFactory {

    fun  getFragment(quest: Question) : Fragment {
        return when (quest.type) {
            AnsType.SEEK_BAR.value -> SeekAnsFragment(quest)
            AnsType.BINARY.value -> RadioAnsFragment(quest)
            AnsType.SCHEDULE.value -> TimeAnsFragment(quest)
            AnsType.FINAL.value -> FinalAnsFragment()
            else -> throw IllegalArgumentException("Question type does not match an AnsType.")
        }
    }

    enum class AnsType(val value: String) {
        SEEK_BAR("SeekBar"),
        BINARY("Binary"),
        SCHEDULE("Schedule"),
        FINAL("Final")
    }
}

