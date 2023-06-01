package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.model.PointModel


interface UserDataRepository {
    suspend fun getUser(userId: String): UserModel
    suspend fun getUserPoint(userId: String): List<PointModel>
}