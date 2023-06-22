package com.space.quizapp.domain.repository


interface AuthenticationRepository {
    suspend fun checkUser(username: String): Boolean

    suspend fun signUpUser(username: String)

    suspend fun signInUser(username: String): Boolean

    fun logOutUser()

    fun getCurrentUserId(): String
}