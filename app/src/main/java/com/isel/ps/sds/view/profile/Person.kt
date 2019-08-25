package com.isel.ps.sds.view.profile

import androidx.lifecycle.LiveData
import androidx.room.*
import java.sql.Date

@Entity(tableName = "person")
data class Person(
    val name: String = "",
    val dateBirth: Date,
    @PrimaryKey val nif: Int,
    val SosContact: SosContact = SosContact(),
    val quiz: String = ""
)

data class SosContact(
    val name: String = "",
    val phoneNumber: Int = 0
)

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Query("SELECT * FROM person")
    fun getPerson(): LiveData<Person>

    @Query("UPDATE person SET name = :name, dateBirth = :dateBirth, nif = :nif, SosContact = :sosContact WHERE nif = :nif")
    fun updatePerson(name: String, dateBirth: Date, nif: Int, sosContact: SosContact)
}
