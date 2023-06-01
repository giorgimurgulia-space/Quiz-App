package com.space.quizapp.domain.usecase.auth

import com.space.quizapp.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    suspend fun checkUser(username: String) = authenticationRepository.checkUser(username)

    suspend fun signInUser(username: String) = authenticationRepository.signInUser(username)

    suspend fun signUpUser(username: String) = authenticationRepository.signUpUser(username)

    fun getCurrentUserId() = authenticationRepository.getCurrentUserId()

}