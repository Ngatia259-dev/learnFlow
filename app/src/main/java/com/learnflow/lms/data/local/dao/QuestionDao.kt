package com.learnflow.lms.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learnflow.lms.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE quizId = :quizId ORDER BY `order` ASC")
    fun getQuestionsByQuizId(quizId: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE id = :questionId")
    fun getQuestionById(questionId: String): Flow<QuestionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Query("SELECT COUNT(*) FROM questions WHERE quizId = :quizId")
    fun getQuestionCount(quizId: String): Flow<Int>
}
