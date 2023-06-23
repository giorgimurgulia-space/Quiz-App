package com.space.quizapp.presentation.quiz.adapter

import androidx.recyclerview.widget.DiffUtil
import com.space.quizapp.presentation.model.AnswerUIModel
import com.space.quizapp.presentation.model.PointUIModel

class AnswerDiffUtil : DiffUtil.ItemCallback<AnswerUIModel>() {

    override fun areItemsTheSame(oldItem: AnswerUIModel, newItem: AnswerUIModel): Boolean {
        return oldItem.answerId == newItem.answerId
    }

    override fun areContentsTheSame(oldItem: AnswerUIModel, newItem: AnswerUIModel): Boolean {
        return oldItem.answerTitle == newItem.answerTitle
    }
}