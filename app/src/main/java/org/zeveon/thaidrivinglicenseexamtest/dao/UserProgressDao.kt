package org.zeveon.thaidrivinglicenseexamtest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.zeveon.thaidrivinglicenseexamtest.entity.UserProgress

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress")
    suspend fun getAll(): List<UserProgress>

    @Query("SELECT * FROM user_progress WHERE correctAnswered = 0 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWrongAnswer(): UserProgress?

    @Insert
    suspend fun insertAll(userProgress: UserProgress)
}