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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import org.zeveon.thaidrivinglicenseexamtest.entity.Question
import org.zeveon.thaidrivinglicenseexamtest.model.QuestionViewModel
import java.util.Locale

@Composable
fun TimedQuestionScreen(navController: NavController, viewModel: QuestionViewModel) {
    val questions by viewModel.currentQuestions.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()

    val totalQuestions = 50
    val totalTimeInMinutes = 50

    var startTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var timePassed by remember { mutableLongStateOf(0L) }
    var timeLeft by remember { mutableLongStateOf(totalTimeInMinutes * 60 * 1000L) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                startTime = System.currentTimeMillis() - timePassed
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                timePassed = System.currentTimeMillis() - startTime
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadRandomQuestions(totalQuestions)
        while (timeLeft > 0) {
            delay(1000L)
            timePassed = System.currentTimeMillis() - startTime
            timeLeft = totalTimeInMinutes * 60 * 1000L - timePassed
        }

        finishTest(navController, viewModel, totalQuestions, questions, selectedAnswers, timePassed)
    }

    val timeLeftFormatted = String.format(
        Locale.getDefault(),
        "%02d:%02d",
        (timeLeft / (60 * 1000)) % 60,
        (timeLeft / 1000) % 60
    )

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
                    Text(
                        text = timeLeftFormatted,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
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
                                    "A"
                                ) }
                            )
                            AnswerOption(
                                option = "B",
                                text = question.optionB,
                                correctAnswer = question.answer,
                                selectedAnswer = selectedAnswers[currentQuestionIndex],
                                onSelectAnswer = { viewModel.selectAnswer(
                                    currentQuestionIndex,
                                    "B"
                                ) }
                            )
                            AnswerOption(
                                option = "C",
                                text = question.optionC,
                                correctAnswer = question.answer,
                                selectedAnswer = selectedAnswers[currentQuestionIndex],
                                onSelectAnswer = { viewModel.selectAnswer(
                                    currentQuestionIndex,
                                    "C"
                                ) }
                            )
                            AnswerOption(
                                option = "D",
                                text = question.optionD,
                                correctAnswer = question.answer,
                                selectedAnswer = selectedAnswers[currentQuestionIndex],
                                onSelectAnswer = { viewModel.selectAnswer(
                                    currentQuestionIndex,
                                    "D"
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
                        onClick = {
                            if (currentQuestionIndex == questions.size - 1) {
                                finishTest(
                                    navController,
                                    viewModel,
                                    totalQuestions,
                                    questions,
                                    selectedAnswers,
                                    timePassed
                                )
                            } else {
                                viewModel.nextQuestion()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = if (currentQuestionIndex == questions.size - 1) "Finish" else "Next")
                    }
                }
            }
        }
    }
}

private fun finishTest(
    navController: NavController,
    viewModel: QuestionViewModel,
    totalQuestions: Int,
    questions: List<Question>,
    selectedAnswers: Map<Int, String>,
    timePassed: Long
) {
    viewModel.resetProgress()
    val correctAnswers = selectedAnswers.count { (index, selected) ->
        questions.getOrNull(index)?.answer == selected
    }
    val timePassedFormatted = String.format(
        Locale.getDefault(),
        "%02d:%02d",
        (timePassed / (60 * 1000)) % 60,
        (timePassed / 1000) % 60
    )
    navController.navigate("test_result/$totalQuestions/$correctAnswers/$timePassedFormatted")
}
