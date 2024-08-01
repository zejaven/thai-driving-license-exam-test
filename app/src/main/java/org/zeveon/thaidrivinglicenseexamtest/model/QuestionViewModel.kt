package org.zeveon.thaidrivinglicenseexamtest.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.zeveon.thaidrivinglicenseexamtest.entity.Question
import org.zeveon.thaidrivinglicenseexamtest.repository.QuestionRepository

class QuestionViewModel(private val repository: QuestionRepository) : ViewModel() {
    private val _currentQuestions = MutableStateFlow<List<Question>>(emptyList())
    val currentQuestions: StateFlow<List<Question>> get() = _currentQuestions

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> get() = _currentQuestionIndex

    private val _selectedAnswers = MutableStateFlow(mutableMapOf<Int, String>())
    val selectedAnswers: StateFlow<Map<Int, String>> get() = _selectedAnswers

    fun loadQuestionsByCategory(category: String?) {
        viewModelScope.launch {
            val questions = if (category == null) {
                repository.getAllQuestions()
            } else {
                repository.getQuestionsByCategory(category)
            }
            _currentQuestions.value = questions
            _currentQuestionIndex.value = 0
        }
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < _currentQuestions.value.size - 1) {
            _currentQuestionIndex.value += 1
        }
    }

    fun previousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value -= 1
        }
    }

    fun selectAnswer(questionIndex: Int, answer: String) {
        val updatedAnswers = _selectedAnswers.value.toMutableMap()
        updatedAnswers[questionIndex] = answer
        _selectedAnswers.value = updatedAnswers
    }
}