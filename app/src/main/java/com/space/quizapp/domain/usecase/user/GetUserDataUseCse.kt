package com.space.quizapp.domain.usecase.user

import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject

class GetUserDataUseCse @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    suspend fun invoke(userId: String) = userDataRepository.getUser(userId)
}