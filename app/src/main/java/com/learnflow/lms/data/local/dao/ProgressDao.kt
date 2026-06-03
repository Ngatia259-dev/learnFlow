package com.learnflow.lms.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learnflow.lms.data.local.entity.ProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM user_progress WHERE userId = :userId AND courseId = :courseId")
    fun getProgressByCourse(userId: String, courseId: String): Flow<List<ProgressEntity>>

    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    fun getAllProgress(userId: String): Flow<List<ProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: ProgressEntity)

    @Query("SELECT COUNT(*) FROM user_progress WHERE userId = :userId AND courseId = :courseId AND completed = 1")
    fun getCompletedCount(userId: String, courseId: String): Flow<Int>

    @Query("SELECT * FROM user_progress WHERE userId = :userId AND courseId = :courseId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastProgress(userId: String, courseId: String): ProgressEntity?

    @Query("DELETE FROM user_progress WHERE userId = :userId")
    suspend fun deleteAllProgress(userId: String)
}
