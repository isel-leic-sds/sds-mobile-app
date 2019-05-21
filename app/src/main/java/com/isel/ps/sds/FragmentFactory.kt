package com.isel.ps.sds

import androidx.fragment.app.Fragment
import com.isel.ps.sds.view.quiz.data.Question
import com.isel.ps.sds.view.quiz.fragments.RadioAnsFragment
import com.isel.ps.sds.view.quiz.fragments.SeekAnsFragment
import com.isel.ps.sds.view.quiz.fragments.TimeAnsFragment

class FragmentFactory {

    fun  getFragment(quest : Question):Fragment {
        if ("SeekBar".equals(quest.id)) {
            return SeekAnsFragment(quest)
        } else if ("Binary".equals(quest.id)) {
            return RadioAnsFragment(quest)
        } else if("Schedule".equals(quest.id)){
            return TimeAnsFragment(quest)
        } else if("END".equals(quest.id)){
            return Fragment() //TODO fragmento final
        } else {
            throw RuntimeException("unknown fragment associated to yout question")
        }
        return Fragment()
    }
}