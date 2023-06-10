package com.space.quizapp.presentation.quiz

import androidx.recyclerview.widget.DiffUtil
import com.space.quizapp.presentation.model.AnswerUIModel
import com.space.quizapp.presentation.model.PointUIModel

class AnswerDiffUtil : DiffUtil.ItemCallback<AnswerUIModel>() {

    override fun areItemsTheSame(oldItem: AnswerUIModel, newItem: AnswerUIModel): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: AnswerUIModel, newItem: AnswerUIModel): Boolean {
        return false
    }
}