package org.zeveon.thaidrivinglicenseexamtest.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LearningScreen(navController: NavController) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Learning",
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(onClick = { navController.navigate("question_screen/full_test") }) {
                    Text(text = "Pass Full Test")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("question_screen/Automotive Law") }) {
                    Text(text = "Automotive Law")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("question_screen/Traffic Law") }) {
                    Text(text = "Traffic Law")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("question_screen/Traffic Signs and Signals") }) {
                    Text(text = "Traffic Signs and Signals")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("question_screen/Etiquette and Awareness") }) {
                    Text(text = "Etiquette and Awareness")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("question_screen/Safe Driving Techniques") }) {
                    Text(text = "Safe Driving Techniques")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("question_screen/Vehicle Maintenance") }) {
                    Text(text = "Vehicle Maintenance")
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(onClick = { navController.navigate("main_menu") }) {
                        Text(text = "Main Menu")
                    }
                }
            }
        }
    }
}