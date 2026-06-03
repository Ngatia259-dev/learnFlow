package com.learnflow.lms.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String,
    val role: String,
    val points: Int = 0,
    val level: Int = 1,
    val streak: Int = 0,
    val coursesEnrolled: Int = 0,
    val coursesCompleted: Int = 0
)
