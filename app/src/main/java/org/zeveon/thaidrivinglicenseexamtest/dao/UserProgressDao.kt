package org.zeveon.thaidrivinglicenseexamtest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.zeveon.thaidrivinglicenseexamtest.entity.UserProgress

@Dao
interface UserProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(userProgress: UserProgress)

    @Query("SELECT * FROM user_progress WHERE category = :category")
    suspend fun getProgressByCategory(category: String?): List<UserProgress>

    @Query("SELECT * FROM user_progress WHERE category IS NULL")
    suspend fun getFullTestProgress(): List<UserProgress>

    @Query("DELETE FROM user_progress WHERE category = :category")
    suspend fun clearProgressByCategory(category: String?)

    @Query("DELETE FROM user_progress WHERE category IS NULL")
    suspend fun clearFullTestProgress()
}