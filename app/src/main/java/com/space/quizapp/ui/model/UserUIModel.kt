package com.space.quizapp.ui.model

import com.space.quizapp.domain.model.UserPoint

data class UserUIModel(
    val userId: String,
    val username: String,
    val userGPA: String,
    val userPoints: List<UserPoint>
)
