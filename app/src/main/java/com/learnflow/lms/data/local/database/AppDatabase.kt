package com.learnflow.lms.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learnflow.lms.data.local.dao.AchievementDao
import com.learnflow.lms.data.local.dao.CourseDao
import com.learnflow.lms.data.local.dao.LessonDao
import com.learnflow.lms.data.local.dao.ProgressDao
import com.learnflow.lms.data.local.dao.QuestionDao
import com.learnflow.lms.data.local.dao.QuizDao
import com.learnflow.lms.data.local.dao.UserDao
import com.learnflow.lms.data.local.entity.AchievementEntity
import com.learnflow.lms.data.local.entity.CourseEntity
import com.learnflow.lms.data.local.entity.LessonEntity
import com.learnflow.lms.data.local.entity.ProgressEntity
import com.learnflow.lms.data.local.entity.QuestionEntity
import com.learnflow.lms.data.local.entity.QuizEntity
import com.learnflow.lms.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        CourseEntity::class,
        LessonEntity::class,
        QuizEntity::class,
        QuestionEntity::class,
        ProgressEntity::class,
        AchievementEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao
    abstract fun lessonDao(): LessonDao
    abstract fun quizDao(): QuizDao
    abstract fun questionDao(): QuestionDao
    abstract fun progressDao(): ProgressDao
    abstract fun achievementDao(): AchievementDao
}
