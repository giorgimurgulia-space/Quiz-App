package com.space.quizapp.presentation.point

import androidx.recyclerview.widget.DiffUtil
import com.space.quizapp.presentation.model.PointUIModel
import com.space.quizapp.presentation.model.QuizUIModel

class PointDiffUtil : DiffUtil.ItemCallback<PointUIModel>() {

    override fun areItemsTheSame(oldItem: PointUIModel, newItem: PointUIModel): Boolean {
        return oldItem.subjectId == newItem.subjectId
    }

    override fun areContentsTheSame(oldItem: PointUIModel, newItem: PointUIModel): Boolean {
        return oldItem.point == newItem.point
    }
}