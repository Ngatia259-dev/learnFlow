package com.learnflow.lms.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val instructorId: String,
    val instructorName: String,
    val thumbnailUrl: String,
    val category: String,
    val duration: String,
    val totalLessons: Int,
    val completedLessons: Int = 0,
    val rating: Float = 0f,
    val enrolledCount: Int = 0,
    val price: String = "Free",
    val isEnrolled: Boolean = false,
    val isCompleted: Boolean = false,
    val difficulty: String = "Beginner",
    val language: String = "English",
    val lastAccessed: Long = System.currentTimeMillis()
)
