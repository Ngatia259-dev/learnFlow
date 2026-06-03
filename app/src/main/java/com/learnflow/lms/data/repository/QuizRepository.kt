package com.learnflow.lms.data.repository

import com.learnflow.lms.data.local.dao.QuestionDao
import com.learnflow.lms.data.local.dao.QuizDao
import com.learnflow.lms.data.local.entity.QuestionEntity
import com.learnflow.lms.data.local.entity.QuizEntity
import com.learnflow.lms.util.MockData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepository @Inject constructor(
    private val quizDao: QuizDao,
    private val questionDao: QuestionDao
) {
    fun getQuizzesByCourseId(courseId: String): Flow<List<QuizEntity>> {
        return quizDao.getQuizzesByCourseId(courseId)
    }

    fun getQuizById(quizId: String): Flow<QuizEntity?> {
        return quizDao.getQuizById(quizId)
    }

    fun getQuestionsByQuizId(quizId: String): Flow<List<QuestionEntity>> {
        return questionDao.getQuestionsByQuizId(quizId)
    }

    suspend fun insertMockQuizzes() {
        quizDao.insertQuizzes(MockData.quizzes)
        questionDao.insertQuestions(MockData.questions)
    }

    suspend fun updateQuizScore(quizId: String, score: Int) {
        quizDao.updateQuizScore(quizId, score)
    }

    fun getCompletedQuizzes(): Flow<List<QuizEntity>> {
        return quizDao.getCompletedQuizzes()
    }

    fun getAverageScore(courseId: String): Flow<Float?> {
        return quizDao.getAverageScore(courseId)
    }

    fun getQuestionCount(quizId: String): Flow<Int> {
        return questionDao.getQuestionCount(quizId)
    }
}
