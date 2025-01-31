package org.zeveon.thaidrivinglicenseexamtest.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import org.zeveon.thaidrivinglicenseexamtest.model.QuestionViewModel

@Composable
fun QuestionScreen(navController: NavController, viewModel: QuestionViewModel, category: String?) {
    val questions by viewModel.currentQuestions.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()
    val incorrectAnswers by viewModel.incorrectAnswers.collectAsState()

    LaunchedEffect(category) {
        viewModel.loadQuestionsByCategory(category)
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { navController.navigate("main_menu") }) {
                        Text(text = "Main Menu")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${currentQuestionIndex + 1}/${questions.size}",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { viewModel.resetProgress(category) }) {
                        Text(text = "Reset")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 72.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        questions.getOrNull(currentQuestionIndex)?.let { question ->
                            Text(
                                text = question.question,
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            question.image?.let { imageName ->
                                if (imageName.isNotBlank()) {
                                    val imagePath = "file:///android_asset/${question.category}/$imageName"
                                    Image(
                                        painter = rememberAsyncImagePainter(imagePath),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .padding(8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                            AnswerOption(
                                option = "A",
                                text = question.optionA,
                                correctAnswer = question.answer,
                                selectedAnswer = selectedAnswers[currentQuestionIndex],
                                onSelectAnswer = { viewModel.selectAnswer(
                                    currentQuestionIndex,
                                    "A",
                                    category
                                ) }
                            )
                            AnswerOption(
                                option = "B",
                                text = question.optionB,
                                correctAnswer = question.answer,
                                selectedAnswer = selectedAnswers[currentQuestionIndex],
                                onSelectAnswer = { viewModel.selectAnswer(
                                    currentQuestionIndex,
                                    "B",
                                    category
                                ) }
                            )
                            AnswerOption(
                                option = "C",
                                text = question.optionC,
                                correctAnswer = question.answer,
                                selectedAnswer = selectedAnswers[currentQuestionIndex],
                                onSelectAnswer = { viewModel.selectAnswer(
                                    currentQuestionIndex,
                                    "C",
                                    category
                                ) }
                            )
                            AnswerOption(
                                option = "D",
                                text = question.optionD,
                                correctAnswer = question.answer,
                                selectedAnswer = selectedAnswers[currentQuestionIndex],
                                onSelectAnswer = { viewModel.selectAnswer(
                                    currentQuestionIndex,
                                    "D",
                                    category
                                ) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Row {
                    Button(
                        onClick = { viewModel.previousQuestion() },
                        enabled = currentQuestionIndex > 0,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Previous")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { viewModel.nextQuestion() },
                        enabled = currentQuestionIndex < questions.size - 1 || incorrectAnswers.isNotEmpty(),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Next")
                    }
                }
            }
        }
    }
}
