package org.zeveon.thaidrivinglicenseexamtest.repository

import org.zeveon.thaidrivinglicenseexamtest.dao.QuestionDao
import org.zeveon.thaidrivinglicenseexamtest.entity.Question

class QuestionRepository(private val questionDao: QuestionDao) {
    suspend fun getRandomQuestionByCategory(category: String): Question? {
        return questionDao.getRandomQuestionByCategory(category)
    }

    suspend fun insertAll(questions: List<Question>) {
        questionDao.insertAll(*questions.toTypedArray())
    }
}