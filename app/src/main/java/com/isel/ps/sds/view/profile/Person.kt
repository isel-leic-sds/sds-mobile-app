package com.isel.ps.sds.view.profile

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.sql.Date

@Entity(tableName = "person")
data class Person(
    val name: String = "",
    val dateBirth: Date,
    @PrimaryKey val nif: Int,
    val SosContact: SosContact = SosContact(),
    val quiz: String = ""
) {
    override fun toString(): String {
        return "Person(name='$name', dateBirth=$dateBirth, nif=$nif, SosContact=$SosContact, quiz='$quiz')"
    }
}

data class SosContact(
    val name: String = "",
    val phoneNumber: Int = 0
) {
    override fun toString(): String {
        return "SosContact(name='$name', phoneNumber=$phoneNumber)"
    }
}

class UserDateConverter {
    @TypeConverter
    fun fromString(value: String?): Date? = Date.valueOf(value)

    @TypeConverter
    fun dateToString(date: Date?): String? = date?.toString()
}

class UserSosContactConverter {
    @TypeConverter
    fun fromString(value: String?): SosContact {
        if (value == null) {
            return SosContact()
        }
        val gson = Gson()
        return gson.fromJson(value, SosContact::class.java)
    }

    @TypeConverter
    fun quizToString(sosContact: SosContact?): String? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(sosContact)
    }
}

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Query("SELECT * FROM person")
    fun getPerson(): LiveData<Person>

    @Query("UPDATE person SET name = :name, dateBirth = :dateBirth, nif = :nif, SosContact = :sosContact WHERE nif = :nif")
    fun updatePerson(name: String, dateBirth: Date, nif: Int, sosContact: SosContact)
}
