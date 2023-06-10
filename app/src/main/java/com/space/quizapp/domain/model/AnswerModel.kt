package com.space.quizapp.domain.model

import com.space.quizapp.common.AnswerStatus

data class AnswerModel(
    val answerId: String,
    val answerTitle: String,
    var answerStatus: AnswerStatus?
)

