package com.space.quizapp.domain.usecase.start

import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.repository.UserRepository
import javax.inject.Inject

class StartUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun invoke(userName:String) = userRepository.startUser(userName)

}