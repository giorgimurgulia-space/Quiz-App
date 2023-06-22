package com.space.quizapp.domain.usecase.quiz

import com.space.quizapp.domain.repository.CurrentQuizRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNextAnswersUseCase @Inject constructor(
    private val currentQuizRepository: CurrentQuizRepository
) {
    fun invoke() = flow {
        emit(currentQuizRepository.getQuizAnswers(currentQuizRepository.getUserAnswers().size))
    }
}