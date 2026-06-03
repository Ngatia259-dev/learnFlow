package com.learnflow.lms.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey
    val id: String,
    val courseId: String,
    val title: String,
    val description: String,
    val questionCount: Int,
    val timeLimit: Int,
    val passingScore: Int,
    val attempts: Int = 0,
    val bestScore: Int = 0,
    val isCompleted: Boolean = false
)
