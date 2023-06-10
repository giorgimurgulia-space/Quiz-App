package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.AnswerModel
import com.space.quizapp.domain.model.QuizModel


interface CurrentQuizRepository {

    suspend fun getQuizById(subjectId: String)

    fun startQuiz(): QuizModel

    fun getQuestion(): String

    fun getAnswers(): List<AnswerModel>

}