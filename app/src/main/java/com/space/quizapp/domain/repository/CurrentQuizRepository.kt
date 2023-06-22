package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.AnswerModel
import com.space.quizapp.domain.model.QuestionModel
import com.space.quizapp.domain.model.QuizModel


interface CurrentQuizRepository {

    suspend fun getQuizById(subjectId: String)

    fun startQuiz(): QuizModel

    fun getQuestion(questionIndex: Int): QuestionModel

    fun getQuizAnswers(questionIndex: Int): List<AnswerModel>

    fun insertUserAnswer(userAnswerIndex: Int)

    fun getCorrectAnswers(): List<Int>

    fun getUserAnswers(): List<Int>
}