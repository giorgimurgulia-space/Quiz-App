package com.space.quizapp.domain.usecase.start

import com.space.quizapp.domain.repository.UserRepository
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val userRepository: UserRepository) {


    suspend fun checkUser(username: String): Boolean {
        return userRepository.checkUser(username)
    }
    suspend fun signInUser(userId: String): Boolean {
        userRepository.signInUser(userId)

        return userId == userRepository.getCurrentUser().userId
    }

    suspend fun signUpUser(username: String): String {
        return userRepository.signUpUser(username).userId
    }

    suspend fun getUserId(username: String): String {
        return userRepository.getUser(username).userId
    }

}