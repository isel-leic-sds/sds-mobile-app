package com.isel.ps.sds.view.quiz

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.fasterxml.jackson.databind.JsonSerializer
import com.isel.ps.sds.R
import com.isel.ps.sds.view.quiz.data.AnswerOptions
import com.isel.ps.sds.view.quiz.data.Question
import com.isel.ps.sds.view.quiz.data.Quiz
import com.isel.ps.sds.view.quiz.data.UserAnswer
import org.json.JSONObject
import java.io.InputStream


object QuizRepository {

    fun loadQuestionData(context: Context) :MutableLiveData<Quiz> {
        val data: MutableLiveData<Quiz> = MutableLiveData()

        val inputStream: InputStream = context.resources.openRawResource(R.raw.quest_ans)
        val inputString = inputStream.bufferedReader().use { it.readText() }
        val obj = JSONObject(inputString)
        val listOfQuestions = obj.getJSONArray("listOfQuestions") //Array of  Questions
        val questList: ArrayList<Question> = ArrayList()


        var values = ArrayList<String>()
        for (i: Int in 0 until listOfQuestions.length()) {
            val quest = listOfQuestions.getJSONObject(i)
            val ans = quest.getJSONObject("possibleAnswers")


            if (ans.has("values")) {
                var jsonValues = ans.getJSONArray("values")

                for (i in 0 until jsonValues.length()) {
                    values.add(jsonValues.getString(i))
                }
            }


            val question = Question(
                quest.getString("type"),
                quest.getString("question"),
                AnswerOptions(
                    ans.optString("option1", ""),
                    ans.optString("option2", ""),
                    values
                ),
                UserAnswer()
            )
            questList.add(question)
        }
        questList.add(Question("Final"))
        val quiz= Quiz(questList)
        data.value = quiz
        return data
    }
}