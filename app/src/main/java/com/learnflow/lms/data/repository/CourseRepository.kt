package com.learnflow.lms.data.repository

import com.learnflow.lms.data.local.dao.CourseDao
import com.learnflow.lms.data.local.dao.LessonDao
import com.learnflow.lms.data.local.entity.CourseEntity
import com.learnflow.lms.data.local.entity.LessonEntity
import com.learnflow.lms.util.MockData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepository @Inject constructor(
    private val courseDao: CourseDao,
    private val lessonDao: LessonDao
) {
    fun getAllCourses(): Flow<List<CourseEntity>> {
        return courseDao.getAllCourses()
    }

    fun getCourseById(courseId: String): Flow<CourseEntity?> {
        return courseDao.getCourseById(courseId)
    }

    fun getCoursesByCategory(category: String): Flow<List<CourseEntity>> {
        return courseDao.getCoursesByCategory(category)
    }

    fun getEnrolledCourses(): Flow<List<CourseEntity>> {
        return courseDao.getEnrolledCourses()
    }

    fun getCompletedCourses(): Flow<List<CourseEntity>> {
        return courseDao.getCompletedCourses()
    }

    fun getAvailableCourses(): Flow<List<CourseEntity>> {
        return courseDao.getAvailableCourses()
    }

    fun searchCourses(query: String): Flow<List<CourseEntity>> {
        return courseDao.searchCourses(query)
    }

    fun getCategories(): Flow<List<String>> {
        return courseDao.getCategories()
    }

    suspend fun insertMockCourses() {
        courseDao.insertCourses(MockData.courses)
        lessonDao.insertLessons(MockData.lessons)
    }

    suspend fun enrollInCourse(courseId: String) {
        courseDao.enrollInCourse(courseId)
    }

    suspend fun updateCourse(course: CourseEntity) {
        courseDao.updateCourse(course)
    }

    suspend fun getLessonsByCourseId(courseId: String): Flow<List<LessonEntity>> {
        return lessonDao.getLessonsByCourseId(courseId)
    }

    suspend fun markLessonComplete(lessonId: String) {
        lessonDao.markLessonComplete(lessonId)
    }

    suspend fun getNextLesson(courseId: String): LessonEntity? {
        return lessonDao.getNextLesson(courseId)
    }
}
