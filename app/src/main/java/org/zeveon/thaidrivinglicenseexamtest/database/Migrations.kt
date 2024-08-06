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

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            UPDATE questions
            SET question = "When a driver encounters a 'Left turn always' sign, what should they do?",
                optionA = "Slow down and turn left immediately",
                optionB = "Wait for the green light before turning left",
                optionC = "Wait for pedestrians and vehicles from the right to pass before turning left",
                optionD = "Turn left immediately"
            WHERE question LIKE "When a driver encounters%"
        """)
    }
}