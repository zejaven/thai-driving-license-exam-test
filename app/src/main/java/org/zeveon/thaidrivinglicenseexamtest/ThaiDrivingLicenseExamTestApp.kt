package org.zeveon.thaidrivinglicenseexamtest

import android.app.Application
import androidx.room.Room
import org.zeveon.thaidrivinglicenseexamtest.component.AppDatabase
import org.zeveon.thaidrivinglicenseexamtest.repository.QuestionRepository

class ThaiDrivingLicenseExamTestApp : Application() {
    lateinit var database: AppDatabase
    lateinit var repository: QuestionRepository

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "driving-license-questions"
        ).build()
        repository = QuestionRepository(database.questionDao())
    }
}