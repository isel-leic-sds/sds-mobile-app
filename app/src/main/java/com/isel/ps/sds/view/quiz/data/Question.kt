package com.isel.ps.sds.view.quiz.data

class Question (
    val type: String = "",
    val question: String = "",
    val answerOptions: AnswerOptions = AnswerOptions(),
    val userAnswer : UserAnswer = UserAnswer()
) {
    override fun toString(): String {
        return "Question(id='$type', question='$question', answerOptions=$answerOptions, userAnswer=$userAnswer)"
    }
}




