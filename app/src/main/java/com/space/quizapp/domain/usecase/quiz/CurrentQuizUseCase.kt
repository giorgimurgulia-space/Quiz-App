package com.space.quizapp.domain.usecase.quiz

import com.space.quizapp.domain.model.QuizModel
import com.space.quizapp.domain.repository.CurrentQuizRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrentQuizUseCase @Inject constructor(
    private val currentQuizRepository: CurrentQuizRepository
) {
    suspend fun startQuiz(subjectId: String): QuizModel {
        currentQuizRepository.getQuizById(subjectId)
        return currentQuizRepository.startQuiz()
    }

    fun getNextQuestion() = currentQuizRepository.getNextQuestion()

    fun getNextAnswer() = flow {
        emit(currentQuizRepository.getNextAnswers())
    }

    fun setUserAnswer(userAnswer: Int) = currentQuizRepository.setUserAnswer(userAnswer)
}