package com.space.quizapp.domain.usecase.quiz

import com.space.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class QuizUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    suspend fun getQuiz() = quizRepository.getAvailableQuizList()
}