package com.space.quizapp.data.repository

import com.space.quizapp.common.AnswerStatus
import com.space.quizapp.common.ApiError
import com.space.quizapp.common.mapper.toAnswer
import com.space.quizapp.common.mapper.toDomainModel
import com.space.quizapp.common.mapper.toQuizDomainModel
import com.space.quizapp.data.remote.api.ApiService
import com.space.quizapp.data.remote.dto.QuizDto
import com.space.quizapp.domain.model.AnswerModel
import com.space.quizapp.domain.model.QuestionModel
import com.space.quizapp.domain.model.QuizModel
import com.space.quizapp.domain.repository.CurrentQuizRepository
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class CurrentQuizRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CurrentQuizRepository {

    private var currentQuiz: AtomicReference<QuizDto> = AtomicReference(null)
    private var currentUserAnswer: AtomicReference<List<Int>> = AtomicReference(emptyList())

    override suspend fun getQuizById(subjectId: String) {
        clearCurrentQuizData()

        val response = apiService.getQuiz()

        if (response.isSuccessful) {
            val quiz = response.body()!!.map {
                it
            }
            val filteredQuizList = quiz.filter {
                it.id == subjectId
            }

            currentQuiz.set(filteredQuizList.firstOrNull())
        } else {
            //todo
            throw ApiError(null)
        }
    }

    override fun startQuiz(): QuizModel {
        return currentQuiz.get().toQuizDomainModel()
    }

    override fun getNextQuestion(): QuestionModel {
        return currentQuiz.get().questions[currentUserAnswer.get().size].toDomainModel()
    }

    override fun getNextAnswers(): List<AnswerModel> {
        return currentQuiz.get().questions[currentUserAnswer.get().size].answers.map { it.toAnswer() }
    }

    override fun setUserAnswer(userAnswerIndex: Int): List<AnswerModel> {
        val correctAnswerIndex =
            currentQuiz.get().questions[currentUserAnswer.get().size].toDomainModel().correctAnswerIndex

        val uiAnswers = currentQuiz.get().questions[currentUserAnswer.get().size].answers.map {
            it.toAnswer().copy(answerStatus = AnswerStatus.NEUTRAL)
        }.toMutableList()

        if (correctAnswerIndex == userAnswerIndex) {
            uiAnswers[userAnswerIndex] = uiAnswers[userAnswerIndex].copy(answerStatus = AnswerStatus.CORRECT)
        } else {
            uiAnswers[userAnswerIndex] = uiAnswers[userAnswerIndex].copy(answerStatus = AnswerStatus.NEGATIVE)
            uiAnswers[correctAnswerIndex] = uiAnswers[correctAnswerIndex].copy(answerStatus = AnswerStatus.POSITIVE)

        }

        insertUserAnswer(userAnswerIndex)

        return uiAnswers
    }

    override fun finishQuiz(): Float {
        var userPoint = 0

        val correctAnswers = currentQuiz.get().questions.map {
            it.toDomainModel().correctAnswerIndex
        }

        currentUserAnswer.get().forEachIndexed { index, item ->
            if (correctAnswers[index] == item)
                userPoint += 1
        }

        return userPoint.toFloat()
    }

    private fun insertUserAnswer(answer: Int) {
        val answers = currentUserAnswer.get() + answer
        currentUserAnswer.set(answers)
    }

    private fun clearCurrentQuizData() {
        currentQuiz.set(null)
        currentUserAnswer.set(emptyList())
    }

}