package com.learnflow.lms.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learnflow.lms.data.local.entity.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements ORDER BY unlocked DESC, points ASC")
    fun getAllAchievements(): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievements WHERE id = :achievementId")
    fun getAchievementById(achievementId: String): Flow<AchievementEntity?>

    @Query("SELECT * FROM achievements WHERE category = :category")
    fun getAchievementsByCategory(category: String): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievements WHERE unlocked = 1")
    fun getUnlockedAchievements(): Flow<List<AchievementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievement: AchievementEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements: List<AchievementEntity>)

    @Query("UPDATE achievements SET unlocked = 1, unlockedAt = :timestamp WHERE id = :achievementId")
    suspend fun unlockAchievement(achievementId: String, timestamp: Long)

    @Query("UPDATE achievements SET progress = :progress WHERE id = :achievementId")
    suspend fun updateProgress(achievementId: String, progress: Int)

    @Query("SELECT COUNT(*) FROM achievements WHERE unlocked = 1")
    fun getUnlockedCount(): Flow<Int>

    @Query("SELECT SUM(points) FROM achievements WHERE unlocked = 1")
    fun getTotalPointsEarned(): Flow<Int?>
}
