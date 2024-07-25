package org.zeveon.thaidrivinglicenseexamtest.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
class UserProgress(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val questionId: Int,
    val correctAnswered: Boolean
)