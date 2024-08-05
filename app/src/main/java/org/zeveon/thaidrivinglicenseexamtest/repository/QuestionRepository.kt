package org.zeveon.thaidrivinglicenseexamtest.repository

import org.zeveon.thaidrivinglicenseexamtest.dao.QuestionDao
import org.zeveon.thaidrivinglicenseexamtest.dao.UserProgressDao
import org.zeveon.thaidrivinglicenseexamtest.entity.Question
import org.zeveon.thaidrivinglicenseexamtest.entity.UserProgress

class QuestionRepository(
    private val questionDao: QuestionDao,
    private val userProgressDao: UserProgressDao
) {

    suspend fun getAllQuestions(): List<Question> {
        return questionDao.getAllQuestions()
    }

    suspend fun getQuestionsByCategory(category: String?): List<Question> {
        return questionDao.getQuestionsByCategory(category)
    }

    suspend fun getQuestionsByIds(ids: List<Int>): List<Question> {
        return questionDao.getQuestionsByIds(ids)
    }

    suspend fun saveProgress(userProgress: UserProgress) {
        userProgressDao.insertProgress(userProgress)
    }

    suspend fun loadProgress(category: String?): List<UserProgress> {
        return if (category == null) {
            userProgressDao.getFullTestProgress()
        } else {
            userProgressDao.getProgressByCategory(category)
        }
    }

    suspend fun resetProgress(category: String?) {
        if (category == null) {
            userProgressDao.clearFullTestProgress()
        } else {
            userProgressDao.clearProgressByCategory(category)
        }
    }

    suspend fun getRandomQuestions(limit: Int): List<Question> {
        return questionDao.getAllQuestions().shuffled().take(limit)
    }
}