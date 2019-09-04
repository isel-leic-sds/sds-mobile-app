package com.isel.ps.sds

import android.content.Context
import android.os.AsyncTask
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.GsonBuilder
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistory
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistoryAnswer
import com.isel.ps.sds.view.clinicalHistory.data.ClinicalHistoryData
import com.isel.ps.sds.view.login.LoginFactory.Login
import com.isel.ps.sds.view.profile.Person
import com.isel.ps.sds.view.profile.PersonDao
import com.isel.ps.sds.view.profile.SosContact
import com.isel.ps.sds.view.quiz.data.*
import org.json.JSONObject
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DhsRepository(
    val context: DigitalHealthSystemApplication,
    private val personDao: PersonDao,
    private val quizDao: QuizDao
) {

    private val baseUrl = "https://sds-web-app.herokuapp.com/sds/api/v1"
    private val historyUrl = "$baseUrl/patients/history"
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

    fun getPatientData(
        queue: RequestQueue,
        onSuccess: (Person) -> Unit,
        onError: (Exception) -> Unit
    ) {
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
            { err -> onError(err) }
        ) {}
        queue.add(request)
    }

    private fun setPatientParameters(person: Person) {
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
        val gson = GsonBuilder().setPrettyPrinting().create()
        body.put(quiz_ID, gson.toJson(quiz))
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

    fun getPatientQuiz(
        queue: RequestQueue,
        onSuccess: (Quiz) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val quizName = sharedPreferences.getString(quiz_ID, defaultValue)
        val request = object : JsonObjectRequest(
            Method.GET,
            "$submitQuizUrl/$quizName",
            null,
            { q -> onSuccess(parseJsonQuiz(q)) },
            { err -> onError(err) }
        ) {}
        queue.add(request)
    }

    fun parseJsonQuiz(quiz: JSONObject): Quiz {
        val questList: ArrayList<Question> = ArrayList()
        val listOfQuestions = quiz.getJSONArray("listOfQuestions")
        val values = ArrayList<String>()
        for (i: Int in 0 until listOfQuestions.length()) {
            val quest = listOfQuestions.getJSONObject(i)
            val ans = quest.getJSONObject("possibleAnswers")
            if (ans.has("values")) {
                val jsonValues = ans.getJSONArray("values")
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
        return Quiz(questList)
    }

    fun submitPerson(person: Person) {
        setPatientParameters(person)
        InsertPersonTask(personDao).execute(person)
    }

    fun getPerson(
        provideNewPerson: (Person?) -> Unit,
        submitNewPerson: (Person) -> Unit,
        onError: (Exception) -> Unit
    ) {
        if (personDao.getPerson().value == null) {
            getPatientData(
                context.requestQueue,
                { p ->
                    provideNewPerson(p)
                    submitNewPerson(p)
                },
                onError
            )
        } else {
            provideNewPerson(personDao.getPerson().value)
        }
    }

    fun submitQuiz(quiz: Quiz) {
        InsertQuizTask(quizDao).execute(quiz)
    }

    fun getQuiz(
        provideNewQuiz: (Quiz?) -> Unit,
        submitNewQuiz: (Quiz) -> Unit,
        onError: (Exception) -> Unit
    ) {
        if (quizDao.getQuiz().value == null) {
            getPatientQuiz(
                context.requestQueue,
                { q ->
                    provideNewQuiz(q)
                    submitNewQuiz(q)
                },
                onError
            )
        } else {
            provideNewQuiz(quizDao.getQuiz().value)
        }
    }

    fun getQuizResults(
        queue: RequestQueue,
        onSuccess: (ClinicalHistory) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val sdsId = sharedPreferences.getString(sdsId_ID, defaultValue)
        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("yyyy/MM/dd")
        val strDate = mdformat.format(calendar.time).split("/")
        val year = strDate[0]
        val month = strDate[1].removePrefix("0")

        val request = object : JsonObjectRequest(
            Method.GET,
            "$historyUrl/$sdsId?month=${month}&year=${year}",
            null,
            { q -> onSuccess(parseJsonQuizResults(q)) },
            { err -> onError(err) }
        ) {}
        queue.add(request)
    }

    fun parseJsonQuizResults(obj: JSONObject): ClinicalHistory {
        val clinicalHistory = obj.getJSONArray("clinicalHistory") //Array ClinicalHistory
        val chDataList: ArrayList<ClinicalHistoryData> = ArrayList()
        var clinicalHistoryData: ClinicalHistoryData

        for (i: Int in 0 until clinicalHistory.length()) {
            val chData = clinicalHistory.getJSONObject(i)
            val type = chData.getString("type")
            val quest = chData.getString("question")
            val answers = chData.getJSONArray("answers")
            clinicalHistoryData = ClinicalHistoryData()
            val answersList: ArrayList<ClinicalHistoryAnswer> = ArrayList()
            var chAnswer: ClinicalHistoryAnswer
            for (i: Int in 0 until answers.length()) {
                val answer = answers.getJSONObject(i)
                val ans = answer.getString("answer")
                val userAnswer = answer.getJSONArray("userAnswer")
                val userAnswers: ArrayList<String> = ArrayList()
                for (i: Int in 0 until userAnswer.length()) {
                    userAnswers.add(userAnswer.get(i).toString())
                }
                chAnswer = ClinicalHistoryAnswer(ans, userAnswers)
                answersList.add(chAnswer)
                clinicalHistoryData = ClinicalHistoryData(type, quest, answersList)
            }
            chDataList.add(clinicalHistoryData)
        }
        chDataList.add(ClinicalHistoryData("Final"))
        return ClinicalHistory(chDataList)
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
