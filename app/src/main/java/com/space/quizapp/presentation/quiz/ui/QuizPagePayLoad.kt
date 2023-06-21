package com.space.quizapp.presentation.quiz.ui

import com.space.quizapp.presentation.model.AnswerUIModel
import com.space.quizapp.common.resource.Result

data class QuizPagePayLoad(
    val quizTitle: String? = null,
    val question: String? = null,
    val answers: Result<List<AnswerUIModel>> = Result.Loading,
    val isLastQuestion: Boolean = false,
    val point: Float? = null
)