package org.zeveon.thaidrivinglicenseexamtest.component

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.zeveon.thaidrivinglicenseexamtest.dao.QuestionDao
import org.zeveon.thaidrivinglicenseexamtest.dao.UserProgressDao
import org.zeveon.thaidrivinglicenseexamtest.database.MIGRATION_1_2
import org.zeveon.thaidrivinglicenseexamtest.entity.Question
import org.zeveon.thaidrivinglicenseexamtest.entity.UserProgress

@Database(entities = [Question::class, UserProgress::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun userProgressDao(): UserProgressDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "driving-license-questions")
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
    }
}