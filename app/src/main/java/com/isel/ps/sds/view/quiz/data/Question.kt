package com.isel.ps.sds.view.quiz.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

data class Question(
    val type: String = "",
    val question: String = "",
    val answerOptions: AnswerOptions = AnswerOptions(),
    val userAnswer : UserAnswer = UserAnswer()
) {
    override fun toString(): String {
        return "Question(id='$type', question='$question', answerOptions=$answerOptions, userAnswer=$userAnswer)"
    }
}

class QuestionConverter {
    @TypeConverter
    fun fromStringToQuestion(value: String?): Question {
        if (value == null) {
            return Question()
        }
        val gson = Gson()
        return gson.fromJson(value, Question::class.java)
    }

    @TypeConverter
    fun fromQuestionToString(question: Question?): String? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(question)
    }

    @TypeConverter
    fun fromStringToQuestions(value: String?): ArrayList<Question> {
        if (value == null) {
            return arrayListOf()
        }
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Question>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromQuestionsToString(questions: ArrayList<Question>): String? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(questions)
    }
}
