package com.isel.ps.sds

import androidx.room.Database
import androidx.room.RoomDatabase
import com.isel.ps.sds.view.profile.Person
import com.isel.ps.sds.view.profile.PersonDao
import com.isel.ps.sds.view.quiz.data.Quiz
import com.isel.ps.sds.view.quiz.data.QuizDao

@Database(entities = [Person::class, Quiz::class], version = 1)
abstract class DhsDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun quizDao(): QuizDao
}