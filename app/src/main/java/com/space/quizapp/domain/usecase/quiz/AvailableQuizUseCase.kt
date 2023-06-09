package com.space.quizapp.domain.usecase.quiz

import com.space.quizapp.domain.repository.AvailableQuizRepository
import javax.inject.Inject

class AvailableQuizUseCase @Inject constructor(
    private val quizRepository: AvailableQuizRepository
) {
    suspend fun getAvailableQuiz() = quizRepository.getAvailableQuizList()
}