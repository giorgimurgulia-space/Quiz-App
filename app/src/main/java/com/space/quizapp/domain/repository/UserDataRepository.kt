package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.model.PointModel
import kotlinx.coroutines.flow.Flow


interface UserDataRepository {
    suspend fun getUser(userId: String): UserModel
    suspend fun getUserPoint(userId: String): List<PointModel>
    suspend fun setUserPoint(
        userId: String,
        subjectId: String,
        quizTitle: String,
        quizDescription: String,
        quizIcon: String,
        point: Float
    )
}