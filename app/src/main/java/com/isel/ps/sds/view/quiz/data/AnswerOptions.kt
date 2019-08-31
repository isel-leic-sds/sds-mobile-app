package com.isel.ps.sds.view.quiz.data

class AnswerOptions(val option1: String = "", val option2: String = "",var values:ArrayList<String> = ArrayList()) : Answer() {
    override fun toString(): String {
        return "AnswerOptions(option1='$option1', option2='$option2', values=$values)"
    }

}