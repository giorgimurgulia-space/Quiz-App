package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.AnswerModel
import com.space.quizapp.domain.model.QuestionModel
import com.space.quizapp.domain.model.QuizModel


interface CurrentQuizRepository {

    suspend fun getQuizById(subjectId: String)

    fun startQuiz(): QuizModel

    fun getNextQuestion(): QuestionModel

    fun getNextAnswers(): List<AnswerModel>

    fun setUserAnswer(userAnswerIndex: Int)

    fun finishQuiz(): Float
}