package org.zeveon.thaidrivinglicenseexamtest.repository

import org.zeveon.thaidrivinglicenseexamtest.dao.QuestionDao
import org.zeveon.thaidrivinglicenseexamtest.entity.Question

class QuestionRepository(private val questionDao: QuestionDao) {

    suspend fun getRandomQuestion(): Question? {
        return questionDao.getRandomQuestion()
    }

    suspend fun getRandomQuestionByCategory(category: String?): Question? {
        return questionDao.getRandomQuestionByCategory(category)
    }
}