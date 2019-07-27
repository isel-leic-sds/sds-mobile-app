package com.isel.ps.sds

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.isel.ps.sds.view.login.LoginFactory.Login
import com.isel.ps.sds.view.profile.Person
import com.isel.ps.sds.view.profile.SosContact
import org.json.JSONObject
import java.io.InputStream
import java.sql.Date

class DhsRepository(val context: DigitalHealthSystemApplication) {

    private val baseUrl = "https://sds-web-app.herokuapp.com/sds/api/v1"
    private val patientUrl = "$baseUrl/patient"
    private val loginUrl = "$patientUrl/login"

    private val SHARED_PREFERENCES_LOGIN_KEY = "LOGIN"
    private val defaultValue = ""
    private val sdsId_ID: String = "sdsID"
    private val password_ID: String = "password"

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

    fun loadPersonData(onSuccess: (Person) -> Unit, onError: (String?) -> Unit) {
        try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.person_data)
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val obj = JSONObject(inputString)
            val sosContact = obj.getJSONObject("sosContact")
            val person = Person(
                obj.getString("name"),
                Date.valueOf(obj.getString("dateOfBirth")),
                obj.getInt("nif"),
//                obj.getInt("phoneNumber"),
                SosContact(
                    sosContact.getString("name"),
                    sosContact.getInt("phoneNumber")
                )
            )
            onSuccess(person)
        } catch (e: Exception) {
            onError(e.message)
        }
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
//                    p.getJSONObject("info").getInt("phoneNumber"),
                        SosContact(
                            p.getJSONObject("info").getJSONObject("contact").getString("name"),
                            p.getJSONObject("info").getJSONObject("contact").getInt("phoneNumber")
                        )
                    )
                )
            },
            {
                it.printStackTrace(); onError(it.message ?: "Error")
            }
        ) {}
        queue.add(request)
    }
}
