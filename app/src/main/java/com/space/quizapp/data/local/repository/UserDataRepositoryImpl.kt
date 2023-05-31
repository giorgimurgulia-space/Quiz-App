package com.space.quizapp.data.local.repository

import com.space.quizapp.data.local.database.model.dao.UserPointDao
import com.space.quizapp.domain.repository.AuthenticationRepository
import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val dao: UserPointDao,
    private val userRepository: AuthenticationRepository
) : UserDataRepository {
    override suspend fun getCurrentUserGPA(): String {
        var currentUserGPA = 0.0.toFloat()
        val currentUserId = userRepository.getCurrentUser().userId
        val userPoints = dao.getUserPoints(currentUserId)

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