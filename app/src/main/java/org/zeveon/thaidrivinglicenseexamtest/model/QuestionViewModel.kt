package org.zeveon.thaidrivinglicenseexamtest.model

import androidx.compose.runtime.toMutableStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.zeveon.thaidrivinglicenseexamtest.entity.Question
import org.zeveon.thaidrivinglicenseexamtest.entity.UserProgress
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
            val progress = repository.loadProgress(category)
            val progressQuestionIds = progress.map { it.questionId }
            val progressQuestionsMap = repository.getQuestionsByIds(progressQuestionIds).associateBy { it.id }
            val progressQuestions = progress.mapNotNull { progressQuestionsMap[it.questionId] }

            val allQuestions = if (category == null) {
                repository.getAllQuestions()
            } else {
                repository.getQuestionsByCategory(category)
            }

            if (progress.isNotEmpty()) {
                val remainingQuestions = allQuestions.filter { question ->
                    question.id !in progressQuestionIds
                }

                val questions = progressQuestions + remainingQuestions

                _currentQuestions.value = questions
                _selectedAnswers.value = progress
                    .mapIndexed { index, it -> index to it.selectedAnswer!! }
                    .toMutableStateMap()

                val incorrectQuestionIds = progress
                    .filter { it.isCorrect != null }
                    .filter { !it.isCorrect!! }
                    .map { it.questionId }
                val incorrectQuestions = repository.getQuestionsByIds(incorrectQuestionIds)
                _incorrectAnswers.value = incorrectQuestions.toMutableSet()

                _currentQuestionIndex.value = progress.size
            } else {
                _currentQuestions.value = allQuestions
                _currentQuestionIndex.value = 0
                _selectedAnswers.value = mutableMapOf()
                _incorrectAnswers.value = mutableSetOf()
            }
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
            _incorrectAnswers.value = mutableSetOf()
            _currentQuestionIndex.value += 1
        }
    }

    fun previousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value -= 1
        }
    }

    fun selectAnswer(questionIndex: Int, answer: String, category: String?) {
        val updatedAnswers = _selectedAnswers.value.toMutableMap()
        updatedAnswers[questionIndex] = answer
        _selectedAnswers.value = updatedAnswers

        saveProgress(questionIndex, answer, category)
    }

    fun selectAnswer(questionIndex: Int, answer: String) {
        val updatedAnswers = _selectedAnswers.value.toMutableMap()
        updatedAnswers[questionIndex] = answer
        _selectedAnswers.value = updatedAnswers
    }

    private fun saveProgress(questionIndex: Int, answer: String, category: String?) {
        viewModelScope.launch {
            val question = _currentQuestions.value[questionIndex]
            val isCorrect = answer == question.answer
            val progress = UserProgress(
                questionId = question.id,
                category = category,
                selectedAnswer = answer,
                isCorrect = isCorrect
            )
            repository.saveProgress(progress)
            if (!isCorrect) {
                val updatedIncorrectAnswers = _incorrectAnswers.value.toMutableSet()
                updatedIncorrectAnswers.add(question)
                _incorrectAnswers.value = updatedIncorrectAnswers
            }
        }
    }

    fun resetProgress(category: String?) {
        viewModelScope.launch {
            repository.resetProgress(category)
            _selectedAnswers.value = mutableMapOf()
            _incorrectAnswers.value = mutableSetOf()
            loadQuestionsByCategory(category)
        }
    }

    fun loadRandomQuestions(questionNumber: Int) {
        viewModelScope.launch {
            val randomQuestions = repository.getRandomQuestions(questionNumber)
            _currentQuestions.value = randomQuestions
            _currentQuestionIndex.value = 0
            _selectedAnswers.value = mutableMapOf()
        }
    }

    fun resetProgress() {
        _selectedAnswers.value = mutableMapOf()
        _incorrectAnswers.value = mutableSetOf()
    }
}