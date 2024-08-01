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

    private val _incorrectAnswers = MutableStateFlow(mutableSetOf<Question>())
    val incorrectAnswers: StateFlow<Set<Question>> get() = _incorrectAnswers

    fun loadQuestionsByCategory(category: String?) {
        viewModelScope.launch {
            val questions = if (category == null) {
                repository.getAllQuestions()
            } else {
                repository.getQuestionsByCategory(category)
            }
            _currentQuestions.value = questions
            _currentQuestionIndex.value = 0
            _selectedAnswers.value.clear()
            _incorrectAnswers.value.clear()
        }
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < _currentQuestions.value.size - 1) {
            _currentQuestionIndex.value += 1
        } else if (_incorrectAnswers.value.isNotEmpty()) {
            val updatedQuestions = _currentQuestions.value.toMutableList().apply {
                addAll(_incorrectAnswers.value)
            }
            _currentQuestions.value = updatedQuestions
            _incorrectAnswers.value.clear()
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

        val question = _currentQuestions.value[questionIndex]
        if (answer != question.answer) {
            val updatedIncorrectAnswers = _incorrectAnswers.value.toMutableSet()
            updatedIncorrectAnswers.add(question)
            _incorrectAnswers.value = updatedIncorrectAnswers
        }
    }
}