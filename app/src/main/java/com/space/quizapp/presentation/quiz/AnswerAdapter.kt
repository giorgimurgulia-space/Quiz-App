package com.space.quizapp.presentation.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.space.quizapp.databinding.ViewAnswerBinding
import com.space.quizapp.databinding.ViewQuizBinding
import com.space.quizapp.presentation.model.AnswerUIModel
import com.space.quizapp.presentation.model.PointUIModel

class AnswerAdapter : ListAdapter<AnswerUIModel, AnswerAdapter.PointViewHolder>(AnswerDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PointViewHolder {
        return PointViewHolder(
            ViewAnswerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PointViewHolder(
        private val binding: ViewAnswerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(answer: AnswerUIModel) = with(binding) {
            root.setContent(answer)
        }
    }
}