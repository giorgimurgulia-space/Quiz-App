package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.UserModel

interface UserRepository {
    suspend fun checkUser(username: String): Boolean

    suspend fun signUpUser(username: String): UserModel

    fun signInUser(userId: String): Boolean

    suspend fun getCurrentUser(): UserModel

    suspend fun getUser(username: String): UserModel
}