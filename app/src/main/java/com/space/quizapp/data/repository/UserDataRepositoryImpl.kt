package com.space.quizapp.data.repository

import com.space.quizapp.common.mapper.toAvailableQuizModel
import com.space.quizapp.common.mapper.toEntity
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
        point: PointModel
    ) {
        val oldPoint =
            userPointDao.getUserSubjectPoint(point.userId, point.subjectId).firstOrNull()?.point

        if (oldPoint == null || point.point > oldPoint) {
            userPointDao.insertUserPoint(point.toEntity())
        }
    }
}