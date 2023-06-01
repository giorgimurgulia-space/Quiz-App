package com.space.quizapp.data.repository

import com.space.quizapp.common.mapper.toDomainModel
import com.space.quizapp.data.local.database.model.dao.UserDao
import com.space.quizapp.data.local.database.model.dao.UserPointDao
import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.repository.AuthenticationRepository
import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userPointDao: UserPointDao,
    private val userDao: UserDao,
    private val userRepository: AuthenticationRepository
) : UserDataRepository {
    override suspend fun getCurrentUser(): UserModel {
        val currentUserId = userRepository.getCurrentUserId()
        return userDao.getUserByIdUser(currentUserId).toDomainModel()
    }

    override suspend fun getCurrentUserGPA(): String {
        var currentUserGPA = 0.0.toFloat()
        val currentUserId = userRepository.getCurrentUserId()
        val userPoints = userPointDao.getUserPoints(currentUserId)

        if (userPoints.isEmpty()) {
            currentUserGPA = 0.toFloat()
        } else {
            userPoints.forEach { point ->
                currentUserGPA += point.point
            }
        }

        return currentUserGPA.toString()
    }
}