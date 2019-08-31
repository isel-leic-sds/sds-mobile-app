package com.isel.ps.sds

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.isel.ps.sds.view.login.LoginFactory.Login
import com.isel.ps.sds.view.profile.Person
import com.isel.ps.sds.view.profile.PersonDao
import com.isel.ps.sds.view.profile.SosContact
import com.isel.ps.sds.view.quiz.data.*
import org.json.JSONObject
import java.sql.Date

class DhsRepository(
    val context: DigitalHealthSystemApplication,
    private val personDao: PersonDao,
    private val quizDao: QuizDao
) {

    private val baseUrl = "https://sds-web-app.herokuapp.com/sds/api/v1"
    private val patientUrl = "$baseUrl/patient"
    private val loginUrl = "$patientUrl/login"
    private val dailyQuizUrl = "$patientUrl/ans"
    private val submitQuizUrl = "$patientUrl/quiz"

    private val SHARED_PREFERENCES_LOGIN_KEY = "LOGIN"
    private val defaultValue = ""
    private val sdsId_ID: String = "sdsID"
    private val password_ID: String = "password"
    private val quiz_ID = "quiz"

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_LOGIN_KEY, Context.MODE_PRIVATE)

    val person: LiveData<Person> = personDao.getPerson()
    val quiz: LiveData<Quiz> = quizDao.getQuiz()

    fun isLoggedIn(): Boolean {
        val sdsId = sharedPreferences.getString(sdsId_ID, defaultValue)
        val password = sharedPreferences.getString(password_ID, defaultValue)

        if (sdsId == defaultValue || password == defaultValue) {
            return false
        }
        return true
    }

    fun setLoginParameters(sdsId: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(sdsId_ID, sdsId)
        editor.putString(password_ID, password)
        editor.apply()
    }

    fun tryLogin(queue: RequestQueue, login: Login, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val body = JSONObject()
        body.put(sdsId_ID, login.sdsID)
        body.put(password_ID, login.password)

        val request = object : JsonObjectRequest(
            Method.POST,
            loginUrl,
            body,
            {
                onSuccess("Login success")
            },
            {
                it.printStackTrace(); onError(it.message ?: "Error")
            }
        ) {}
        queue.add(request)
    }

    fun getPatientData(queue: RequestQueue, onSuccess: (Person) -> Unit, onError: (String) -> Unit) {
        val sdsId = sharedPreferences.getString(sdsId_ID, defaultValue)
        val request = object : JsonObjectRequest(
            Method.GET,
            "$patientUrl/$sdsId",
            null,
            { p ->
                onSuccess(
                    Person(
                        p.getString("name"),
                        Date.valueOf(p.getJSONObject("info").getString("dateOfBirth")),
                        p.getJSONObject("info").getInt("nif"),
                        SosContact(
                            p.getJSONObject("info").getJSONObject("contact").getString("name"),
                            p.getJSONObject("info").getJSONObject("contact").getInt("phoneNumber")
                        ),
                        p.getJSONObject("info").getString("quiz")
                    )
                )
            },
            {
                it.printStackTrace(); onError(it.message ?: "Error")
            }
        ) {}
        queue.add(request)
    }

    fun setPatientParameters(person: Person) {
        val editor = sharedPreferences.edit()
        editor.putString(quiz_ID, person.quiz)
        editor.apply()
    }

    fun submitDailyQuiz(
        queue: RequestQueue,
        quiz: Quiz,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val body = JSONObject()
        body.put(quiz_ID, quiz.questions)
        val sdsId = sharedPreferences.getString(sdsId_ID, defaultValue)

        val request = object : JsonObjectRequest(
            Method.POST,
            "$dailyQuizUrl/$sdsId",
            body,
            { onSuccess() },
            { err -> onError(err) }
        ) {}
        queue.add(request)
    }

    fun getPatientQuiz(queue: RequestQueue, onSuccess: (Quiz) -> Unit, onError: (String) -> Unit) {
        val quizName = sharedPreferences.getString(quiz_ID, defaultValue)
        val request = object : JsonObjectRequest(
            Method.GET,
            "$submitQuizUrl/$quizName",
            null,
            { q -> onSuccess(parseJsonQuiz(q)) },
            {
                it.printStackTrace(); onError(it.message ?: "Error")
            }
        ) {}
        queue.add(request)
    }

    fun parseJsonQuiz(quiz: JSONObject): Quiz {
        val questList: ArrayList<Question> = ArrayList()
        val listOfQuestions = quiz.getJSONArray("listOfQuestions")
        for (i: Int in 0 until listOfQuestions.length()) {
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
        questList.add(Question("Final"))
        return Quiz(questList)
    }

    fun submitPerson(person: Person) {
        InsertPersonTask(personDao).execute(person)
    }

    fun submitQuiz(quiz: Quiz) {
        InsertQuizTask(quizDao).execute(quiz)
    }
}

class InsertPersonTask(private val personDao: PersonDao) : AsyncTask<Person, Void, Unit>() {
    override fun doInBackground(vararg person: Person) {
        personDao.insertPerson(person[0])
    }
}

class InsertQuizTask(private val quizDao: QuizDao) : AsyncTask<Quiz, Void, Unit>() {
    override fun doInBackground(vararg quiz: Quiz) {
        quizDao.insertQuiz(quiz[0])
    }
}
