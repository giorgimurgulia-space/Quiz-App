package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.UserModel

interface AuthenticationRepository {
    suspend fun checkUser(username: String): Boolean

    suspend fun signUpUser(username: String): Boolean

    suspend fun signInUser(username: String): Boolean

    suspend fun getCurrentUser(): UserModel
}