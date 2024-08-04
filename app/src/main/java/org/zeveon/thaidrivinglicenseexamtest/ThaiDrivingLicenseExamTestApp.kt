package org.zeveon.thaidrivinglicenseexamtest

import android.app.Application
import org.zeveon.thaidrivinglicenseexamtest.component.AppDatabase
import org.zeveon.thaidrivinglicenseexamtest.repository.QuestionRepository

class ThaiDrivingLicenseExamTestApp : Application() {
    lateinit var database: AppDatabase
    lateinit var repository: QuestionRepository

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
        repository = QuestionRepository(database.questionDao(), database.userProgressDao())
    }
}