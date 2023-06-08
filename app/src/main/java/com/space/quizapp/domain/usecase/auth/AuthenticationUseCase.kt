package com.space.quizapp.domain.usecase.auth

import com.space.quizapp.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    suspend fun invoke(username: String): Boolean {
        if (checkUser(username)) {
            return signInUser(username)
        } else {
            signUpUser(username)
            return signInUser(username)
        }
    }

    fun getCurrentUserId() = authenticationRepository.getCurrentUserId()

    fun logOut() = authenticationRepository.logOutUser()

    private suspend fun checkUser(username: String) = authenticationRepository.checkUser(username)

    private suspend fun signInUser(username: String) = authenticationRepository.signInUser(username)

    private suspend fun signUpUser(username: String) = authenticationRepository.signUpUser(username)

}