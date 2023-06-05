package com.space.quizapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.space.quizapp.databinding.LayoutQuizBinding
import com.space.quizapp.presentation.model.QuizUIModel

class QuizAdapter() : ListAdapter<QuizUIModel, QuizAdapter.QuizViewHolder>(QuizDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizViewHolder {
        return QuizViewHolder(
            LayoutQuizBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class QuizViewHolder(
        private val binding: LayoutQuizBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(quiz: QuizUIModel) = with(binding) {
            titleText.text = quiz.quizTitle
            descriptionText.text = quiz.quizDescription
        }

    }
}