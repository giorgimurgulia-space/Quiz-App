package com.space.quizapp.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.space.quizapp.ui.model.QuizUIModel

class QuizDiffUtil : DiffUtil.ItemCallback<QuizUIModel>() {

    override fun areItemsTheSame(oldItem: QuizUIModel, newItem: QuizUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: QuizUIModel, newItem: QuizUIModel): Boolean {
        return oldItem.quizTitle == newItem.quizTitle
    }
}