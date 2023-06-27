package com.space.quizapp.domain.usecase.quiz

import com.space.quizapp.domain.model.QuestionModel
import com.space.quizapp.domain.repository.CurrentQuizRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNextQuestionUseCase @Inject constructor(
    private val currentQuizRepository: CurrentQuizRepository
) {

    fun invoke(): QuestionModel {
        return currentQuizRepository.getQuestion(currentQuizRepository.getUserAnswers().size)
    }
}