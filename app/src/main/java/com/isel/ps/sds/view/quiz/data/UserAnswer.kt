package com.isel.ps.sds.view.quiz.data

class UserAnswer(var finalAnswer : String = "") : Answer() {
    override fun toString(): String {
        return "UserAnswer(finalAnswer='$finalAnswer')"
    }
}
