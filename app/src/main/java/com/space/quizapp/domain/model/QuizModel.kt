package com.space.quizapp.domain.model


data class QuizModel(
    val id: String,
    val quizTitle: String,
    val questionsCount: Int,
    val questions: List<QuestionModel>
)