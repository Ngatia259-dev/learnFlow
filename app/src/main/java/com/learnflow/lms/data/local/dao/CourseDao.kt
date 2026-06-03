package com.learnflow.lms.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.learnflow.lms.data.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses ORDER BY lastAccessed DESC")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE id = :courseId")
    fun getCourseById(courseId: String): Flow<CourseEntity?>

    @Query("SELECT * FROM courses WHERE category = :category")
    fun getCoursesByCategory(category: String): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE isEnrolled = 1")
    fun getEnrolledCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE isEnrolled = 1 AND isCompleted = 1")
    fun getCompletedCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE isEnrolled = 0")
    fun getAvailableCourses(): Flow<List<CourseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Update
    suspend fun updateCourse(course: CourseEntity)

    @Query("UPDATE courses SET isEnrolled = 1 WHERE id = :courseId")
    suspend fun enrollInCourse(courseId: String)

    @Query("UPDATE courses SET completedLessons = completedLessons + 1 WHERE id = :courseId")
    suspend fun incrementCompletedLessons(courseId: String)

    @Query("SELECT * FROM courses WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchCourses(query: String): Flow<List<CourseEntity>>

    @Query("SELECT DISTINCT category FROM courses")
    fun getCategories(): Flow<List<String>>
}
