package org.zeveon.thaidrivinglicenseexamtest.util

import android.content.Context
import android.util.Log
import com.opencsv.CSVReader
import org.zeveon.thaidrivinglicenseexamtest.entity.Question
import java.io.InputStreamReader

fun parseCSV(context: Context, fileName: String): List<Question> {
    val questions = mutableListOf<Question>()
    try {
        context.assets.open(fileName).use { inputStream ->
            InputStreamReader(inputStream).use { inputStreamReader ->
                CSVReader(inputStreamReader).use { reader ->
                    val lines = reader.readAll()
                    for (i in 1 until lines.size) {
                        val tokens = lines[i]
                        val question = Question(
                            question = tokens[0],
                            optionA = tokens[1],
                            optionB = tokens[2],
                            optionC = tokens[3],
                            optionD = tokens[4],
                            answer = tokens[5],
                            image = tokens.getOrNull(6),
                            category = tokens[7]
                        )
                        questions.add(question)
                    }
                }
            }
        }
    } catch (e: Exception) {
        Log.e("parseCSV", "Error parsing CSV", e)
    }
    return questions
}