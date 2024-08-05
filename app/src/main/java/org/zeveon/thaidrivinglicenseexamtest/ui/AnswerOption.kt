package org.zeveon.thaidrivinglicenseexamtest.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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