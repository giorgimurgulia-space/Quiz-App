package com.space.quizapp.domain.usecase.user

import com.space.quizapp.domain.model.PointModel
import com.space.quizapp.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserPointsUseCse @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    suspend fun invoke(userId: String): Flow<List<PointModel>> = flow {
        val userPoints = userDataRepository.getUserPoint(userId)
        emit(userPoints)
    }
}