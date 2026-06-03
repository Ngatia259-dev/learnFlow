package com.learnflow.lms.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learnflow.lms.data.local.entity.QuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query("SELECT * FROM quizzes WHERE courseId = :courseId")
    fun getQuizzesByCourseId(courseId: String): Flow<List<QuizEntity>>

    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    fun getQuizById(quizId: String): Flow<QuizEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: QuizEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizzes(quizzes: List<QuizEntity>)

    @Query("UPDATE quizzes SET attempts = attempts + 1, bestScore = CASE WHEN :score > bestScore THEN :score ELSE bestScore END, isCompleted = 1 WHERE id = :quizId")
    suspend fun updateQuizScore(quizId: String, score: Int)

    @Query("SELECT * FROM quizzes WHERE isCompleted = 1")
    fun getCompletedQuizzes(): Flow<List<QuizEntity>>

    @Query("SELECT AVG(bestScore) FROM quizzes WHERE courseId = :courseId AND isCompleted = 1")
    fun getAverageScore(courseId: String): Flow<Float?>
}
