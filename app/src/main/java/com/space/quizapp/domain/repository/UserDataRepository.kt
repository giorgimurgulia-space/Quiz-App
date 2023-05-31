package com.space.quizapp.domain.repository


interface UserDataRepository {
    suspend fun getCurrentUserGPA(): String
}