package com.space.quizapp.data.repository

import com.space.quizapp.common.AnswerStatus
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
                        question.answers.indexOf(question.correctAnswer),
                        question.questionIndex
                    )
                })
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
        return currentQuiz.get()
    }

    override fun getNextQuestion(): QuestionModel {
//        return if (currentUserAnswer.get().isEmpty())
//            currentQuiz.get().questions[currentUserAnswer.get().size]
//        else
//            currentQuiz.get().questions[currentUserAnswer.get().size + 1]

        return currentQuiz.get().questions[currentUserAnswer.get().size]

    }

    override fun getNextAnswers(): List<AnswerModel> {
//        return if (currentUserAnswer.get().isEmpty())
//            currentQuiz.get().questions[currentUserAnswer.get().size].answers
//        else
//            currentQuiz.get().questions[currentUserAnswer.get().size + 1].answers

        return currentQuiz.get().questions[currentUserAnswer.get().size].answers

    }

    override fun setUserAnswer(userAnswer: Int): List<AnswerModel> {
        val correctAnswer =
            currentQuiz.get().questions[currentUserAnswer.get().size].correctAnswerIndex

        val uiAnswers = currentQuiz.get().questions[currentUserAnswer.get().size].answers.map {
            it.copy(answerStatus = AnswerStatus.NEUTRAL)
        }

        if (currentUserAnswer.equals(userAnswer)) {
            uiAnswers[userAnswer].answerStatus = AnswerStatus.CORRECT
        } else {
            uiAnswers[userAnswer].answerStatus = AnswerStatus.NEGATIVE
            uiAnswers[correctAnswer].answerStatus = AnswerStatus.POSITIVE
        }

        insertUserAnswer(userAnswer)

        return uiAnswers
    }

    override fun finishQuiz(): Float {
        val correctAnswers = currentQuiz.get().questions.map {
            it.answers
        }

        var userPoint = 0

        currentUserAnswer.get().forEachIndexed { index, item ->
            if (correctAnswers[index].equals(item))
                userPoint += 1
        }

        currentQuiz.set(null)
        currentUserAnswer.set(null)

        return userPoint.toFloat()
    }

    private fun getNewId(): String {
        return UUID.randomUUID().toString()
    }

    private fun insertUserAnswer(answer: Int) {
        val answers = currentUserAnswer.get() + answer

        currentUserAnswer.set(answers)
    }

}