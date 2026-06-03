package com.learnflow.lms.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learnflow.lms.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("UPDATE users SET points = points + :points WHERE id = :userId")
    suspend fun addPoints(userId: String, points: Int)

    @Query("UPDATE users SET streak = :streak WHERE id = :userId")
    suspend fun updateStreak(userId: String, streak: Int)

    @Query("UPDATE users SET coursesEnrolled = coursesEnrolled + 1 WHERE id = :userId")
    suspend fun incrementCoursesEnrolled(userId: String)

    @Query("UPDATE users SET coursesCompleted = coursesCompleted + 1 WHERE id = :userId")
    suspend fun incrementCoursesCompleted(userId: String)

    @Query("SELECT * FROM users WHERE role = 'student' ORDER BY points DESC")
    fun getLeaderboard(): Flow<List<UserEntity>>
}
