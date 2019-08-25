package com.isel.ps.sds.view.quiz.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "quiz")
data class Quiz(val questions: ArrayList<Question>)

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuiz(quiz: Quiz)

    @Query("SELECT * FROM quiz")
    fun getQuiz(): LiveData<Quiz>

    @Query("UPDATE quiz SET questions = :questions")
    fun updateQuiz(questions: ArrayList<Question>)
}

