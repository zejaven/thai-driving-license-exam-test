package org.zeveon.thaidrivinglicenseexamtest.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.zeveon.thaidrivinglicenseexamtest.entity.Question
import org.zeveon.thaidrivinglicenseexamtest.repository.QuestionRepository

class QuestionViewModel(private val repository: QuestionRepository) : ViewModel() {
    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> get() = _currentQuestion

    fun loadRandomQuestion(category: String?) {
        viewModelScope.launch {
            val question = if (category == null) {
                repository.getRandomQuestion()
            } else {
                repository.getRandomQuestionByCategory(category)
            }
            _currentQuestion.value = question
        }
    }
}