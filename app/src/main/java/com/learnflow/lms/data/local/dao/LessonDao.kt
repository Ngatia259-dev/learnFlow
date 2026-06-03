package com.learnflow.lms.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learnflow.lms.data.local.entity.LessonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons WHERE courseId = :courseId ORDER BY `order` ASC")
    fun getLessonsByCourseId(courseId: String): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    fun getLessonById(lessonId: String): Flow<LessonEntity?>

    @Query("SELECT * FROM lessons WHERE courseId = :courseId AND `order` = :order")
    suspend fun getLessonByOrder(courseId: String, order: Int): LessonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: LessonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Query("UPDATE lessons SET isCompleted = 1 WHERE id = :lessonId")
    suspend fun markLessonComplete(lessonId: String)

    @Query("SELECT COUNT(*) FROM lessons WHERE courseId = :courseId AND isCompleted = 1")
    fun getCompletedLessonCount(courseId: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM lessons WHERE courseId = :courseId")
    fun getTotalLessonCount(courseId: String): Flow<Int>

    @Query("SELECT * FROM lessons WHERE courseId = :courseId AND isCompleted = 0 ORDER BY `order` ASC LIMIT 1")
    suspend fun getNextLesson(courseId: String): LessonEntity?
}
