package org.zeveon.thaidrivinglicenseexamtest.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.zeveon.thaidrivinglicenseexamtest.model.QuestionViewModel

@Composable
fun QuestionScreen(navController: NavController, viewModel: QuestionViewModel, category: String) {
    val question by viewModel.currentQuestion.collectAsState()

    LaunchedEffect(category) {
        viewModel.loadRandomQuestion(category)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { navController.navigateUp() }) {
            Text(text = "Main Menu")
        }
        Spacer(modifier = Modifier.height(16.dp))

        question?.let {
            Text(text = it.question, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.optionA, modifier = Modifier.clickable { checkAnswer(it.optionA, it.answer) })
            Text(text = it.optionB, modifier = Modifier.clickable { checkAnswer(it.optionB, it.answer) })
            Text(text = it.optionC, modifier = Modifier.clickable { checkAnswer(it.optionC, it.answer) })
            Text(text = it.optionD, modifier = Modifier.clickable { checkAnswer(it.optionD, it.answer) })
        }
    }
}

fun checkAnswer(selected: String, correct: String) {
    if (selected == correct) {

    } else {

    }
}