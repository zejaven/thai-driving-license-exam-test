package org.zeveon.thaidrivinglicenseexamtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.zeveon.thaidrivinglicenseexamtest.factory.QuestionViewModelFactory
import org.zeveon.thaidrivinglicenseexamtest.model.QuestionViewModel
import org.zeveon.thaidrivinglicenseexamtest.ui.QuestionScreen
import org.zeveon.thaidrivinglicenseexamtest.ui.theme.ThaiDrivingLicenseExamTestTheme
import org.zeveon.thaidrivinglicenseexamtest.util.parseCSV

class MainActivity : ComponentActivity() {
    private val viewModel: QuestionViewModel by viewModels {
        QuestionViewModelFactory((application as ThaiDrivingLicenseExamTestApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = (application as ThaiDrivingLicenseExamTestApp).database
        val questionDao = db.questionDao()

        lifecycleScope.launch {
            if (questionDao.getRandomQuestionByCategory("Automotive Law") == null) {
                val questions = withContext(Dispatchers.IO) {
                    parseCSV(this@MainActivity, "questions.csv")
                }
                questionDao.insertAll(*questions.toTypedArray())
            }

            setContent {
                ThaiDrivingLicenseExamTestTheme {
                    QuestionScreen(viewModel = viewModel, category = "Automotive Law")
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ThaiDrivingLicenseExamTestTheme {
            QuestionScreen(viewModel = viewModel, category = "Automotive Law")
        }
    }
}
