package com.isel.ps.sds.view.quiz

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.R
import com.isel.ps.sds.view.quiz.data.*
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.json.JSONObject
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

object QuizRepository {

    fun loadQuestionData(context: Context) :MutableLiveData<Quiz> {
            var data: MutableLiveData<Quiz> = MutableLiveData();

            val inputStream: InputStream = context.resources.openRawResource(R.raw.quest_ans)
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val obj = JSONObject(inputString)
            val listOfQuestions = obj.getJSONArray("listOfQuestions") //Array of  Questions

            val questList: ArrayList<Question> = ArrayList()

            for (i: Int in 0..(listOfQuestions.length() - 1)) {
                val quest = listOfQuestions.getJSONObject(i)
                val ans = quest.getJSONObject("possibleAnswers")

                val question = Question(
                    quest.getString("type"),
                    quest.getString("question"),
                    AnswerOptions(
                        ans.optString("option1", ""),
                        ans.optString("option2", "")
                    ),
                    UserAnswer()
                )
                questList.add(question)
            }

        questList.add(Question("END"))
        val quiz= Quiz(questList)
        data.value=quiz;
        return data

    }



}