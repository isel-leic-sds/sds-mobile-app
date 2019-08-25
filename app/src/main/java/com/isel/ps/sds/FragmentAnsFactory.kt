package com.isel.ps.sds

import androidx.fragment.app.Fragment
import com.isel.ps.sds.view.quiz.data.Question
import com.isel.ps.sds.view.quiz.fragments.FinalAnsFragment
import com.isel.ps.sds.view.quiz.fragments.RadioAnsFragment
import com.isel.ps.sds.view.quiz.fragments.SeekAnsFragment
import com.isel.ps.sds.view.quiz.fragments.TimeAnsFragment

class FragmentAnsFactory {

    fun  getFragment(quest: Question) : Fragment {
        return when (AnsType.valueOf(quest.type)) {
            AnsType.SEEK_BAR -> SeekAnsFragment(quest)
            AnsType.BINARY -> RadioAnsFragment(quest)
            AnsType.SCHEDULE -> TimeAnsFragment(quest)
            AnsType.FINAL -> FinalAnsFragment()
        }
    }

    enum class AnsType(val value: String) {
        SEEK_BAR("SeekBar"),
        BINARY("Binary"),
        SCHEDULE("Schedule"),
        FINAL("Final")
    }
}

