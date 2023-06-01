package com.space.quizapp.domain.repository


interface AuthenticationRepository {
    suspend fun checkUser(username: String): Boolean

    suspend fun signUpUser(username: String): Boolean

    suspend fun signInUser(username: String): Boolean

    fun getCurrentUserId(): String
}