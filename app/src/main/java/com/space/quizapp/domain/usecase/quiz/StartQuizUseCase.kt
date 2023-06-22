package com.space.quizapp.domain.usecase.quiz

import com.space.quizapp.domain.model.QuizModel
import com.space.quizapp.domain.repository.CurrentQuizRepository
import javax.inject.Inject

class StartQuizUseCase @Inject constructor(
    private val currentQuizRepository: CurrentQuizRepository
) {
    suspend fun invoke(subjectId: String): QuizModel {
        currentQuizRepository.getQuizById(subjectId)
        return currentQuizRepository.startQuiz()
    }
}