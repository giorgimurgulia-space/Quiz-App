package com.space.quizapp.domain.usecase.user

import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject

class GetUserGpaUseCse @Inject constructor(
    private val userDataRepository: UserDataRepository
) {

    suspend fun invoke(userId: String): String {
        var currentUserGPA = 0.0.toFloat()
        val userPoints = userDataRepository.getUserPoint(userId)

        if (userPoints.isEmpty()) {
            currentUserGPA = 0.toFloat()
        } else {
            userPoints.forEach { point ->
                currentUserGPA += point.point
            }
        }

        return currentUserGPA.toString()
    }
}