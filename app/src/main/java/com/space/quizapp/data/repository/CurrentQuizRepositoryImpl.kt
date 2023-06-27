package com.space.quizapp.data.repository

import com.space.quizapp.common.resource.ApiError
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
            throw ApiError(null)
        }
    }

    override fun startQuiz(): QuizModel {
        return currentQuiz.get().toQuizDomainModel()
    }

    override fun getQuestion(questionIndex: Int): QuestionModel {
        return currentQuiz.get().questions[questionIndex].toDomainModel()
    }

    override fun getUserAnswers(): List<Int> {
        return currentUserAnswer.get()
    }

    override fun getQuizAnswers(questionIndex: Int): List<AnswerModel> {
        return currentQuiz.get().questions[questionIndex].answers.map { it.toAnswer() }
    }

    override fun insertUserAnswer(userAnswerIndex: Int) {
        addAnswerToList(userAnswerIndex)
    }

    override fun getCorrectAnswers(): List<Int> {
        return currentQuiz.get().questions.map {
            it.toDomainModel().correctAnswerIndex
        }
    }

    private fun addAnswerToList(answer: Int) {
        val answers = currentUserAnswer.get() + answer
        currentUserAnswer.set(answers)
    }

    private fun clearCurrentQuizData() {
        currentQuiz.set(null)
        currentUserAnswer.set(emptyList())
    }

}