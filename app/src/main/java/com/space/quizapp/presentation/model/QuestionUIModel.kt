package com.space.quizapp.presentation.model

data class QuestionUIModel(
    val question: String,
    val answers: List<AnswerUIModel>,
    val isLastQuestion: Boolean
)