package com.space.quizapp.presentation.model

data class PointUIModel(
    val userId: String,
    val subjectId: String,
    val quizTitle: String,
    val quizDescription: String,
    val quizIcon: String,
    var point: Float
)