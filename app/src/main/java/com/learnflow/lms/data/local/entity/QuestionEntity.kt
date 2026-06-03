package com.learnflow.lms.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey
    val id: String,
    val quizId: String,
    val questionText: String,
    val questionType: String,
    val options: String,
    val correctAnswer: String,
    val explanation: String,
    val order: Int
)
