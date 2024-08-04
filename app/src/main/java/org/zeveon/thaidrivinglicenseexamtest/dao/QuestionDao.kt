package org.zeveon.thaidrivinglicenseexamtest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.zeveon.thaidrivinglicenseexamtest.entity.Question

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions ORDER BY RANDOM()")
    suspend fun getAllQuestions(): List<Question>

    @Query("SELECT * FROM questions WHERE category = :category ORDER BY RANDOM()")
    suspend fun getQuestionsByCategory(category: String?): List<Question>

    @Query("SELECT * FROM questions WHERE id IN (:ids)")
    suspend fun getQuestionsByIds(ids: List<Int>): List<Question>

    @Insert
    suspend fun insertAll(vararg questions: Question)
}