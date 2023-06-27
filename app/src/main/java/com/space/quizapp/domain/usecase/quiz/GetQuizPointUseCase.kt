package com.space.quizapp.domain.usecase.quiz

import com.space.quizapp.domain.repository.CurrentQuizRepository
import javax.inject.Inject

class GetQuizPointUseCase @Inject constructor(
    private val currentQuizRepository: CurrentQuizRepository
) {
    fun invoke(): Float {
        var userPoint = 0

        val correctAnswers = currentQuizRepository.getCorrectAnswers()

        currentQuizRepository.getUserAnswers().forEachIndexed { index, item ->
            if (correctAnswers[index] == item)
                userPoint += 1
        }

        return userPoint.toFloat()
    }
}