package com.isel.ps.sds

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.isel.ps.sds.view.profile.Person
import com.isel.ps.sds.view.profile.PersonDao
import com.isel.ps.sds.view.profile.UserDateConverter
import com.isel.ps.sds.view.profile.UserSosContactConverter
import com.isel.ps.sds.view.quiz.data.QuestionConverter
import com.isel.ps.sds.view.quiz.data.Quiz
import com.isel.ps.sds.view.quiz.data.QuizConverter
import com.isel.ps.sds.view.quiz.data.QuizDao

@Database(entities = [Person::class, Quiz::class], version = 1, exportSchema = false)
@TypeConverters(
    UserDateConverter::class,
    UserSosContactConverter::class,
    QuizConverter::class,
    QuestionConverter::class
)
abstract class DhsDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun quizDao(): QuizDao
}