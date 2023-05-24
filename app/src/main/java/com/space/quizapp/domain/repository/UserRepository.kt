package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.UserModel

interface UserRepository {
    suspend fun startUser(userName: String): UserModel
}