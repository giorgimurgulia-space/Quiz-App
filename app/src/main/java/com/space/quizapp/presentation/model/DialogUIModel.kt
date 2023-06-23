package com.space.quizapp.presentation.model

data class DialogUIModel(
    val icon: Boolean = false,
    val title: Int? = null,
    val description: String? = null,
    val yesButton: (() -> Unit)? = null,
    val closeButton: (() -> Unit)? = null
)