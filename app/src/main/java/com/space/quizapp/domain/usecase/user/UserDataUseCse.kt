package com.space.quizapp.domain.usecase.user

import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject

class UserDataUseCse @Inject constructor(
    private val userDataRepository: UserDataRepository
) {

    suspend fun getCurrentUser() = userDataRepository.getCurrentUser()


    suspend fun getCurrentUserGPA(): String {
        return userDataRepository.getCurrentUserGPA()
    }

}