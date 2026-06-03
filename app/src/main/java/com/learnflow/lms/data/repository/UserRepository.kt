package com.learnflow.lms.data.repository

import com.learnflow.lms.data.local.dao.UserDao
import com.learnflow.lms.data.local.entity.UserEntity
import com.learnflow.lms.util.MockData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getUserById(userId: String): Flow<UserEntity?> {
        return userDao.getUserById(userId)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun insertMockUsers() {
        userDao.insertUsers(MockData.users)
    }

    suspend fun addPoints(userId: String, points: Int) {
        userDao.addPoints(userId, points)
    }

    suspend fun updateStreak(userId: String, streak: Int) {
        userDao.updateStreak(userId, streak)
    }

    fun getLeaderboard(): Flow<List<UserEntity>> {
        return userDao.getLeaderboard()
    }

    suspend fun login(email: String, password: String): UserEntity? {
        // Mock authentication - accept any email with password "password"
        return if (password == "password") {
            userDao.getUserByEmail(email)
        } else {
            null
        }
    }
}
