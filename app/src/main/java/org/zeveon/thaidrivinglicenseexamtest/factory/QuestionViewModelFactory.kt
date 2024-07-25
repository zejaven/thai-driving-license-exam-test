package org.zeveon.thaidrivinglicenseexamtest.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.zeveon.thaidrivinglicenseexamtest.model.QuestionViewModel
import org.zeveon.thaidrivinglicenseexamtest.repository.QuestionRepository

class QuestionViewModelFactory(private val repository: QuestionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}