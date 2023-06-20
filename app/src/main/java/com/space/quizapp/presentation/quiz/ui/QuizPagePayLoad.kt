package com.space.quizapp.presentation.quiz.ui

import com.space.quizapp.presentation.model.AnswerUIModel
import com.space.quizapp.common.resource.Result

data class QuizPagePayLoad(
    val quizTitle:String,
    val question: String,
    val answers: Result<List<AnswerUIModel>>,
    val isLastQuestion: Boolean,
    val point: Float?
)