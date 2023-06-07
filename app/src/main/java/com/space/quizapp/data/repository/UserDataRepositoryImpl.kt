package com.space.quizapp.data.repository

import com.space.quizapp.common.mapper.toDomainModel
import com.space.quizapp.data.local.database.model.dao.UserDao
import com.space.quizapp.data.local.database.model.dao.UserPointDao
import com.space.quizapp.domain.model.PointModel
import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userPointDao: UserPointDao,
    private val userDao: UserDao,
) : UserDataRepository {
    override suspend fun getUser(userId: String): UserModel {
        return userDao.getUserById(userId).toDomainModel()
    }

    override suspend fun getUserPoint(userId: String): List<PointModel> {
        val userPoints = userPointDao.getUserPoints(userId).map {
            it.toDomainModel()
        }

        return userPoints
    }
}