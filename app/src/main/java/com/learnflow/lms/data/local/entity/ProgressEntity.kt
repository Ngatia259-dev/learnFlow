package com.learnflow.lms.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class ProgressEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val courseId: String,
    val lessonId: String,
    val completed: Boolean,
    val timestamp: Long,
    val progressPercentage: Int = 0
)
