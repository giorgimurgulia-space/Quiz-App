package com.space.quizapp.domain.usecase.user

import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.model.UserPoint
import com.space.quizapp.domain.repository.AuthenticationRepository
import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject

class UserDataUseCse @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userDataRepository: UserDataRepository
) {

    suspend fun getCurrentUser(): UserModel {
        return authenticationRepository.getCurrentUser()
    }

    suspend fun getCurrentUserGPA(): String {
        return userDataRepository.getCurrentUserGPA()
    }

}