package com.isel.ps.sds

import android.content.Context
import com.isel.ps.sds.view.profile.Person
import com.isel.ps.sds.view.profile.SosContact
import org.json.JSONObject
import java.io.InputStream
import java.sql.Date

class DhsRepository {

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

}