package com.learnflow.lms.util

object Constants {
    const val APP_NAME = "LearnFlow"
    const val DATABASE_NAME = "learnflow_database"
    const val DEFAULT_USER_ID = "user1"

    // Time constants
    const val SPLASH_DELAY = 2500L
    const val ANIMATION_DURATION = 700
    const val DEBOUNCE_DELAY = 300L

    // Quiz constants
    const val DEFAULT_TIME_LIMIT = 10 // minutes
    const val PASSING_SCORE = 70

    // Achievement thresholds
    val STREAK_MILESTONES = listOf(7, 14, 30, 60, 90, 180, 365)
    val LESSON_MILESTONES = listOf(10, 25, 50, 100, 200, 500)
    val QUIZ_MILESTONES = listOf(5, 10, 25, 50, 100)
}
