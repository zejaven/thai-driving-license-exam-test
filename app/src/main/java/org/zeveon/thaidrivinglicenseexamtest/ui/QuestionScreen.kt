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

    LaunchedEffect(category) {
        viewModel.loadRandomQuestion(category)
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

            question?.let {
                Text(
                    text = it.question,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val options = listOf(it.optionA, it.optionB, it.optionC, it.optionD)

                Column(modifier = Modifier.fillMaxWidth()) {
                    options.forEach { option ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .border(1.dp, Color.Black)
                                .clickable { checkAnswer(option, it.answer) }
                                .background(MaterialTheme.colorScheme.surface)
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

fun checkAnswer(selected: String, correct: String) {
    if (selected == correct) {

    } else {

    }
}