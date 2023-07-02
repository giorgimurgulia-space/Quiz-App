package com.space.quizapp.presentation.model

sealed class DialogItem(val viewType: ViewType) {
    enum class ViewType {
        LOADER,
        QUESTION,
        NOTIFICATION
    }

    data class LoaderDialog(
        val isProgressbar: Boolean = true
    ) : DialogItem(ViewType.LOADER)

    data class QuestionDialog(
        val title: Int,
        val onYesButton: (() -> Unit)
    ) : DialogItem(ViewType.QUESTION)

    data class NotificationDialog(
        val icon: Boolean = false,
        val title: Int? = null,
        val description: String? = null,
        val onCloseButton: (() -> Unit)
    ) : DialogItem(ViewType.NOTIFICATION)
}