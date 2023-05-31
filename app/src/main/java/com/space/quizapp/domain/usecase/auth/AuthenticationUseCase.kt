package com.space.quizapp.domain.usecase.auth

import com.space.quizapp.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val userRepository: AuthenticationRepository) {

    suspend fun checkUser(username: String) =  userRepository.checkUser(username)

    suspend fun signInUser(username: String) = userRepository.signInUser(username)

    suspend fun signUpUser(username: String) = userRepository.signUpUser(username)

}