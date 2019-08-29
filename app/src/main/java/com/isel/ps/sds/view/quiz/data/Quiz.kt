package com.isel.ps.sds.view.quiz.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Entity(tableName = "quiz")
data class Quiz(@PrimaryKey val questions: ArrayList<Question> = ArrayList())

class QuizConverter {
    @TypeConverter
    fun toQuizFromString(value: String?): Quiz {
        if (value == null) {
            return Quiz()
        }
        val gson = Gson()
        return gson.fromJson(value, Quiz::class.java)
    }

    @TypeConverter
    fun toStringFromQuiz(quiz: Quiz?): String? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(quiz)
    }
}

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuiz(quiz: Quiz)

    @Query("SELECT * FROM quiz")
    fun getQuiz(): LiveData<Quiz>
}

