package com.learnflow.lms.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey
    val id: String,
    val courseId: String,
    val title: String,
    val description: String,
    val type: String,
    val contentUrl: String,
    val duration: String,
    val order: Int,
    val isCompleted: Boolean = false,
    val isLocked: Boolean = false,
    val videoDuration: Long = 0L
)
