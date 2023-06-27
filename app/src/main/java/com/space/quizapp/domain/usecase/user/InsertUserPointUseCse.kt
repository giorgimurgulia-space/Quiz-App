package com.space.quizapp.domain.usecase.user

import com.space.quizapp.domain.model.PointModel
import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject

class InsertUserPointUseCse @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    suspend fun invoke(point: PointModel) = userDataRepository.setUserPoint(point)
}