package com.space.quizapp.domain.usecase.auth

import com.space.quizapp.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    fun invoke() = authenticationRepository.getCurrentUserId()
}