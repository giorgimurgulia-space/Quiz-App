package com.space.quizapp.domain.model

data class QuestionModel(
    val questionTitle: String,
    val answers: List<String>,
    val correctAnswer: String,
    val questionIndex: Int
)