package com.space.quizapp.presentation.quiz.ui

import com.space.quizapp.presentation.model.AnswerUIModel
import com.space.quizapp.common.resource.Result

data class QuizPagePayLoad(
    val quizTitle: String? = null,
    val question: String? = null,
    val answers: List<AnswerUIModel> = emptyList(),
    val correctAnswerIndex: Int? = null,
    val questionIndex: Int? = null,
    val questionCount: Int? = null
)