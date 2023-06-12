package com.space.quizapp.data.repository

import com.space.quizapp.common.mapper.toAvailableQuizModel
import com.space.quizapp.data.local.database.model.dao.UserDao
import com.space.quizapp.data.local.database.model.dao.UserPointDao
import com.space.quizapp.data.local.database.model.entity.UserPointEntity
import com.space.quizapp.domain.model.PointModel
import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userPointDao: UserPointDao,
    private val userDao: UserDao,
) : UserDataRepository {
    override suspend fun getUser(userId: String): UserModel {
        return userDao.getUserById(userId).toAvailableQuizModel()
    }

    override suspend fun getUserPoint(userId: String): List<PointModel> {
        val userPoints = userPointDao.getUserPoints(userId).map {
            it.toAvailableQuizModel()
        }
        return (userPoints)
    }

    override suspend fun setUserPoint(
        userId: String,
        subjectId: String,
        quizTitle: String,
        quizDescription: String,
        quizIcon: String,
        point: Float
    ) {
        val oldPoint = userPointDao.getUserSubjectPoint(userId, subjectId).firstOrNull()?.point

        if (oldPoint == null || point > oldPoint) {
            userPointDao.insertUserPoint(
                UserPointEntity(userId, subjectId, quizTitle, quizDescription, quizIcon, point)
            )
        }
    }
}