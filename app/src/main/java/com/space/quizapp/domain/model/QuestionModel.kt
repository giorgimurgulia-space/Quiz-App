package com.space.quizapp.domain.model

data class QuestionModel(
    val questionTitle: String,
    val answers: List<AnswerModel>,
    val correctAnswerIndex: Int,
    val questionIndex: Int
)