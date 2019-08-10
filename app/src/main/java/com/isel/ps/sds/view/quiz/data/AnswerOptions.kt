package com.isel.ps.sds.view.quiz.data

class AnswerOptions(val option1: String = "", val option2: String = "") : Answer() {
    override fun toString(): String {
        return "AnswerOptions(option1='$option1', option2='$option2')"
    }
}