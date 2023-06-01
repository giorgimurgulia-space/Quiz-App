package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.UserModel


interface UserDataRepository {
    suspend fun getCurrentUser(): UserModel
    suspend fun getCurrentUserGPA(): String
}