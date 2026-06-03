package com.learnflow.lms.di

import android.content.Context
import androidx.room.Room
import com.learnflow.lms.data.local.dao.AchievementDao
import com.learnflow.lms.data.local.dao.CourseDao
import com.learnflow.lms.data.local.dao.LessonDao
import com.learnflow.lms.data.local.dao.ProgressDao
import com.learnflow.lms.data.local.dao.QuestionDao
import com.learnflow.lms.data.local.dao.QuizDao
import com.learnflow.lms.data.local.dao.UserDao
import com.learnflow.lms.data.local.database.AppDatabase
import com.learnflow.lms.data.local.database.DatabaseCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        callback: DatabaseCallback
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "learnflow_database"
        )
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabaseCallback(): DatabaseCallback {
        return DatabaseCallback(CoroutineScope(SupervisorJob()))
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideCourseDao(database: AppDatabase): CourseDao {
        return database.courseDao()
    }

    @Provides
    fun provideLessonDao(database: AppDatabase): LessonDao {
        return database.lessonDao()
    }

    @Provides
    fun provideQuizDao(database: AppDatabase): QuizDao {
        return database.quizDao()
    }

    @Provides
    fun provideQuestionDao(database: AppDatabase): QuestionDao {
        return database.questionDao()
    }

    @Provides
    fun provideProgressDao(database: AppDatabase): ProgressDao {
        return database.progressDao()
    }

    @Provides
    fun provideAchievementDao(database: AppDatabase): AchievementDao {
        return database.achievementDao()
    }
}
