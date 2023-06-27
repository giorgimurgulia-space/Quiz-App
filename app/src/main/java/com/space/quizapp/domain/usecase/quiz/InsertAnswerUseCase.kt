package com.space.quizapp.domain.usecase.quiz

import com.space.quizapp.domain.repository.CurrentQuizRepository
import javax.inject.Inject

class InsertAnswerUseCase @Inject constructor(
    private val currentQuizRepository: CurrentQuizRepository
) {

    fun invoke(userAnswerIndex: Int) =
        currentQuizRepository.insertUserAnswer(userAnswerIndex)

}