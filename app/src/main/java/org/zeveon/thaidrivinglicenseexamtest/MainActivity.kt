package org.zeveon.thaidrivinglicenseexamtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.zeveon.thaidrivinglicenseexamtest.factory.QuestionViewModelFactory
import org.zeveon.thaidrivinglicenseexamtest.model.QuestionViewModel
import org.zeveon.thaidrivinglicenseexamtest.ui.MainMenuScreen
import org.zeveon.thaidrivinglicenseexamtest.ui.QuestionScreen
import org.zeveon.thaidrivinglicenseexamtest.ui.theme.ThaiDrivingLicenseExamTestTheme
import org.zeveon.thaidrivinglicenseexamtest.util.parseCSV

class MainActivity : ComponentActivity() {
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
                    val navController = rememberNavController()
                    AppNavHost(navController)
                }
            }
        }
    }

    @Composable
    fun AppNavHost(navController: NavHostController) {
        val application = (navController.context.applicationContext as ThaiDrivingLicenseExamTestApp)
        val repository = application.repository
        val factory = QuestionViewModelFactory(repository)

        NavHost(navController = navController, startDestination = "main_menu") {
            composable("main_menu") { MainMenuScreen(navController) }
            composable("question_screen/{category}") { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: "Automotive Law"
                val viewModel: QuestionViewModel = viewModel(factory = factory)
                QuestionScreen(navController = navController, viewModel = viewModel, category = category)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ThaiDrivingLicenseExamTestTheme {
            val navController = rememberNavController()
            AppNavHost(navController)
        }
    }
}
