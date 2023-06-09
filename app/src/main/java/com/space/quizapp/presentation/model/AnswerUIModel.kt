package com.space.quizapp.presentation.model

data class AnswerUIModel(
    val answerId: String,
    val answerTitle: String,
    val answerStatus: AnswerStatus
)

enum class AnswerStatus {
    POSITIVE,
    NEGATIVE,
    NEUTRAL
}