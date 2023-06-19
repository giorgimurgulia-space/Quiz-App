package com.space.quizapp.presentation.model

import com.space.quizapp.common.AnswerStatus

data class AnswerUIModel(
    val answerId: String,
    val answerTitle: String,
    val answerStatus: AnswerStatus?
)