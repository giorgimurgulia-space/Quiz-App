package com.space.quizapp.domain.usecase.auth

import com.space.quizapp.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    suspend fun invoke(username: String): Boolean {
        return if (checkUser(username)) {
            signInUser(username)
        } else {
            signUpUser(username)
            signInUser(username)
        }
    }

    private suspend fun checkUser(username: String) = authenticationRepository.checkUser(username)

    private suspend fun signInUser(username: String) = authenticationRepository.signInUser(username)

    private suspend fun signUpUser(username: String) = authenticationRepository.signUpUser(username)

}