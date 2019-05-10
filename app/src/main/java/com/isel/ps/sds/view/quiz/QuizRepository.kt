package com.isel.ps.sds.view.quiz

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.isel.ps.sds.R
import org.json.JSONObject
import java.io.InputStream
import java.util.*

object QuizRepository {

    fun loadQuestionData(context: Context) :MutableLiveData<Quiz> {
            var data: MutableLiveData<Quiz> = MutableLiveData();

            val inputStream: InputStream = context.resources.openRawResource(R.raw.quest_ans)
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val obj = JSONObject(inputString)
            val listOfQuestions = obj.getJSONArray("listOfQuestions") //Array of  Questions

            val questList: LinkedList<Question> = LinkedList()

            for (i: Int in 0..(listOfQuestions.length() - 1)) {
                val quest = listOfQuestions.getJSONObject(i)
                val ans = quest.getJSONObject("possibleAnswers")

                val question = Question(
                    quest.getString("id"),
                    quest.getString("question"),
                    Answer(
                        ans.optString("option1",""),
                        ans.optString("option2","")
                    )
                )
                questList.add(question)
            }
        val quiz=Quiz(questList)
        data.value=quiz;
        return data

    }
}