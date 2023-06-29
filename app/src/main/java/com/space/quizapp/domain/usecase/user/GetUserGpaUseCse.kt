package com.space.quizapp.domain.usecase.user

import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject

class GetUserGpaUseCse @Inject constructor(
    private val userDataRepository: UserDataRepository
) {

    suspend fun invoke(userId: String): String {
        var currentUserGPA = 0.0.toFloat()
        var questionCount = 0

        val userPoints = userDataRepository.getUserPoint(userId)

        if (userPoints.isEmpty()) {
            return currentUserGPA.toString()
        } else {
            userPoints.forEach { point ->
                currentUserGPA += point.point
                questionCount += point.questionCount
            }
        }

        return (currentUserGPA / questionCount * 4).toString()
    }
}