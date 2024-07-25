package org.zeveon.thaidrivinglicenseexamtest.component

import androidx.room.Database
import androidx.room.RoomDatabase
import org.zeveon.thaidrivinglicenseexamtest.dao.QuestionDao
import org.zeveon.thaidrivinglicenseexamtest.dao.UserProgressDao
import org.zeveon.thaidrivinglicenseexamtest.entity.Question
import org.zeveon.thaidrivinglicenseexamtest.entity.UserProgress

@Database(entities = [Question::class, UserProgress::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun userProgressDao(): UserProgressDao
}