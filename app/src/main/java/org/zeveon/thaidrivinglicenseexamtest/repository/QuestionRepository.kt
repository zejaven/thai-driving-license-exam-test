package org.zeveon.thaidrivinglicenseexamtest.repository

import org.zeveon.thaidrivinglicenseexamtest.dao.QuestionDao
import org.zeveon.thaidrivinglicenseexamtest.entity.Question

class QuestionRepository(private val questionDao: QuestionDao) {

    suspend fun getAllQuestions(): List<Question> {
        return questionDao.getAllQuestions()
    }

    suspend fun getQuestionsByCategory(category: String?): List<Question> {
        return questionDao.getQuestionsByCategory(category)
    }
}