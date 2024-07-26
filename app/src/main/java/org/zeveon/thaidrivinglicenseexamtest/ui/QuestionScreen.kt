package org.zeveon.thaidrivinglicenseexamtest.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.zeveon.thaidrivinglicenseexamtest.model.QuestionViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun QuestionScreen(navController: NavController, viewModel: QuestionViewModel, category: String?) {
    val question by viewModel.currentQuestion.collectAsState()
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var correctAnswer by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(category) {
        viewModel.loadRandomQuestion(category)
        selectedAnswer = null
        correctAnswer = null
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

            question?.let { q ->
                Text(
                    text = q.question,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val correctAnswerText = when (q.answer) {
                    "A" -> q.optionA
                    "B" -> q.optionB
                    "C" -> q.optionC
                    "D" -> q.optionD
                    else -> ""
                }

                val options = listOf(q.optionA, q.optionB, q.optionC, q.optionD)

                Column(modifier = Modifier.fillMaxWidth()) {
                    options.forEach { option ->
                        val backgroundColor = when {
                            selectedAnswer != null && selectedAnswer == option && selectedAnswer != correctAnswerText -> Color.Red
                            selectedAnswer != null && correctAnswerText == option -> Color.Green
                            else -> MaterialTheme.colorScheme.surface
                        }

                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .border(1.dp, Color.Black)
                                .clickable {
                                    if (selectedAnswer == null) {
                                        selectedAnswer = option
                                    }
                                }
                                .background(backgroundColor)
                                .padding(16.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = option,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}