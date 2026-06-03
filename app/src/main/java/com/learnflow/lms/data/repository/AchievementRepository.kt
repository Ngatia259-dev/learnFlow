package com.learnflow.lms.data.repository

import com.learnflow.lms.data.local.dao.AchievementDao
import com.learnflow.lms.data.local.entity.AchievementEntity
import com.learnflow.lms.util.MockData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepository @Inject constructor(
    private val achievementDao: AchievementDao
) {
    fun getAllAchievements(): Flow<List<AchievementEntity>> {
        return achievementDao.getAllAchievements()
    }

    fun getAchievementById(achievementId: String): Flow<AchievementEntity?> {
        return achievementDao.getAchievementById(achievementId)
    }

    fun getAchievementsByCategory(category: String): Flow<List<AchievementEntity>> {
        return achievementDao.getAchievementsByCategory(category)
    }

    fun getUnlockedAchievements(): Flow<List<AchievementEntity>> {
        return achievementDao.getUnlockedAchievements()
    }

    suspend fun insertMockAchievements() {
        achievementDao.insertAchievements(MockData.achievements)
    }

    suspend fun unlockAchievement(achievementId: String) {
        achievementDao.unlockAchievement(achievementId, System.currentTimeMillis())
    }

    suspend fun updateProgress(achievementId: String, progress: Int) {
        achievementDao.updateProgress(achievementId, progress)
    }

    fun getUnlockedCount(): Flow<Int> {
        return achievementDao.getUnlockedCount()
    }

    fun getTotalPointsEarned(): Flow<Int?> {
        return achievementDao.getTotalPointsEarned()
    }
}
