package com.space.quizapp.data.repository

import com.space.quizapp.common.ApiError
import com.space.quizapp.data.remote.api.ApiService
import com.space.quizapp.domain.model.AnswerModel
import com.space.quizapp.domain.model.QuestionModel
import com.space.quizapp.domain.model.QuizModel
import com.space.quizapp.domain.repository.CurrentQuizRepository
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class CurrentQuizRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CurrentQuizRepository {

    private var currentQuiz: AtomicReference<QuizModel> = AtomicReference(null)
    private var currentUserAnswer: AtomicReference<List<Int>> = AtomicReference(emptyList())

    override suspend fun getQuizById(subjectId: String) {
        val response = apiService.getQuiz()

        if (response.isSuccessful) {
            val quiz = response.body()!!.map {

                QuizModel(it.id, it.quizTitle, it.questionsCount, it.questions.map { question ->
                    QuestionModel(
                        question.questionTitle,
                        question.answers.map { answer ->
                            AnswerModel(getNewId(), answer, null)
                        },
                        question.correctAnswer,
                        question.questionIndex
                    )
                })
            }
            quiz.filter {
                it.id == subjectId
            }

            currentQuiz.set(quiz.firstOrNull())
        } else {
            //todo
            throw ApiError(null)
        }
    }

    override fun startQuiz(): QuizModel {

        return currentQuiz.get()
    }

    override fun getQuestion(): String {
        return currentQuiz.get().questions[currentUserAnswer.get().size + 1].questionTitle
    }

    override fun getAnswers(): List<AnswerModel> {
        return currentQuiz.get().questions[currentUserAnswer.get().size + 1].answers
    }

    private fun getNewId(): String {
        return UUID.randomUUID().toString()
    }

}