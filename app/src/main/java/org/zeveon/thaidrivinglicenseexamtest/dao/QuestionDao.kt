package org.zeveon.thaidrivinglicenseexamtest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.zeveon.thaidrivinglicenseexamtest.entity.Question

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE category = :category ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuestionByCategory(category: String): Question?

    @Insert
    suspend fun insertAll(vararg questions: Question)
}