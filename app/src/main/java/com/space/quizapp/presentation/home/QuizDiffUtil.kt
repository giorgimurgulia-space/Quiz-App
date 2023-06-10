package com.space.quizapp.presentation.home

import androidx.recyclerview.widget.DiffUtil
import com.space.quizapp.presentation.model.AvailableQuizUIModel

class QuizDiffUtil : DiffUtil.ItemCallback<AvailableQuizUIModel>() {

    override fun areItemsTheSame(oldItem: AvailableQuizUIModel, newItem: AvailableQuizUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AvailableQuizUIModel, newItem: AvailableQuizUIModel): Boolean {
        return oldItem.quizTitle == newItem.quizTitle
    }
}