package org.zeveon.thaidrivinglicenseexamtest.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.zeveon.thaidrivinglicenseexamtest.model.QuestionViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun QuestionScreen(navController: NavController, viewModel: QuestionViewModel, category: String?) {
    val questions by viewModel.currentQuestions.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()

    LaunchedEffect(category) {
        viewModel.loadQuestionsByCategory(category)
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { navController.navigate("main_menu") }) {
                Text(text = "Main Menu")
            }
            Spacer(modifier = Modifier.height(16.dp))

            questions.getOrNull(currentQuestionIndex)?.let { question ->
                Text(text = question.question, style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(8.dp))
                AnswerOption(
                    option = "A",
                    text = question.optionA,
                    correctAnswer = question.answer,
                    selectedAnswer = selectedAnswers[currentQuestionIndex],
                    onSelectAnswer = { viewModel.selectAnswer(currentQuestionIndex, "A") }
                )
                AnswerOption(
                    option = "B",
                    text = question.optionB,
                    correctAnswer = question.answer,
                    selectedAnswer = selectedAnswers[currentQuestionIndex],
                    onSelectAnswer = { viewModel.selectAnswer(currentQuestionIndex, "B") }
                )
                AnswerOption(
                    option = "C",
                    text = question.optionC,
                    correctAnswer = question.answer,
                    selectedAnswer = selectedAnswers[currentQuestionIndex],
                    onSelectAnswer = { viewModel.selectAnswer(currentQuestionIndex, "C") }
                )
                AnswerOption(
                    option = "D",
                    text = question.optionD,
                    correctAnswer = question.answer,
                    selectedAnswer = selectedAnswers[currentQuestionIndex],
                    onSelectAnswer = { viewModel.selectAnswer(currentQuestionIndex, "D") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(
                    onClick = {
                        viewModel.previousQuestion()
                    },
                    enabled = currentQuestionIndex > 0,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Previous")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        viewModel.nextQuestion()
                    },
                    enabled = currentQuestionIndex < questions.size - 1,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}

@Composable
fun AnswerOption(
    option: String,
    text: String,
    correctAnswer: String,
    selectedAnswer: String?,
    onSelectAnswer: () -> Unit
) {
    val isSelected = selectedAnswer != null
    val isThisOptionSelected = selectedAnswer == option
    val isCorrect = selectedAnswer == correctAnswer
    val isThisOptionCorrect = option == correctAnswer

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { if (!isSelected) onSelectAnswer() },
        color = when {
            isSelected && isThisOptionSelected && !isCorrect -> Color.Red
            isSelected && isThisOptionCorrect -> Color.Green
            else -> MaterialTheme.colorScheme.surface
        },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}