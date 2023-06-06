package com.space.quizapp.data.remote.dto

data class QuizDto(
    val id: String,
    val quizTitle: String,
    val quizDescription: String,
    val quizIcon:String,
    val questionsCount: Int,
    val questions: List<QuestionDto>
)