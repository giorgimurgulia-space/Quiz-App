package com.space.quizapp.domain.usecase.quiz

import com.space.quizapp.domain.repository.CurrentQuizRepository
import com.space.quizapp.presentation.model.QuizUIModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrentQuizUseCase @Inject constructor(
    private val currentQuizRepository: CurrentQuizRepository
) {
    suspend fun startQuiz(subjectId: String): QuizUIModel {
        currentQuizRepository.getQuizById(subjectId)
        val quiz = currentQuizRepository.startQuiz()
        return QuizUIModel(quiz.id, quiz.quizTitle, quiz.questionsCount)
    }

    fun getNextQuestion() = currentQuizRepository.getNextQuestion()

    fun getNextAnswer() = flow {
        emit(currentQuizRepository.getNextAnswers())
    }

    fun setUserAnswer(userAnswer: Int) = flow {
        emit(currentQuizRepository.setUserAnswer(userAnswer))
    }

    fun finishQuiz() = currentQuizRepository.finishQuiz()
}