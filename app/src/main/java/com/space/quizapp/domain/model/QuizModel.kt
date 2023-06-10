package com.space.quizapp.domain.model

import com.space.quizapp.data.remote.dto.QuestionDto

data class QuizModel(
    val id: String,
    val quizTitle: String,
    val questionsCount: Int,
    val questions: List<QuestionDto>
)