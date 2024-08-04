package org.zeveon.thaidrivinglicenseexamtest.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE user_progress_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                questionId INTEGER NOT NULL,
                category TEXT,
                selectedAnswer TEXT,
                isCorrect INTEGER DEFAULT NULL
            )
        """)

        db.execSQL("DROP TABLE user_progress")

        db.execSQL("ALTER TABLE user_progress_new RENAME TO user_progress")
    }
}