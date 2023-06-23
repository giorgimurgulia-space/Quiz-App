package com.space.quizapp.data.remote.dto

data class QuestionDto(
    val questionTitle: String,
    val answers: List<String>,
    val correctAnswer: String,
    val questionIndex: Int
)