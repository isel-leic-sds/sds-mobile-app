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

class DhsRepository {

    private val baseUrl = "https://sds-web-app.herokuapp.com"
    private val loginUrl = "$baseUrl/sds/api/v1/patient/login"

    private val sdsID: String = "sdsID"
    private val password: String = "password"

    fun loadPersonData(context: Context, onSuccess: (Person) -> Unit, onError: (String?) -> Unit) {
        try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.person_data)
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val obj = JSONObject(inputString)
            val sosContact = obj.getJSONObject("sosContact")
            val person = Person(
                obj.getString("name"),
                Date.valueOf(obj.getString("dateOfBirth")),
                obj.getInt("nif"),
                obj.getInt("phoneNumber"),
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
        body.put(sdsID, login.sdsID)
        body.put(password, login.password)

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
}
