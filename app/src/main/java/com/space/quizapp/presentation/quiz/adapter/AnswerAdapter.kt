package com.space.quizapp.presentation.quiz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.space.quizapp.databinding.ViewAnswerBinding
import com.space.quizapp.presentation.model.AnswerUIModel

class AnswerAdapter(private val onItemClicked: ((answerIndex: Int) -> Unit)) :
    ListAdapter<AnswerUIModel, AnswerAdapter.PointViewHolder>(AnswerDiffUtil()) {

    var correctAnswer: Int? = null
    private var userAnswer: Int? = null

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
        ) {
            onAnswerClick(it)
        }
    }


    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            correctAnswer!!,
            userAnswer
        )
    }

    override fun submitList(list: List<AnswerUIModel>?) {
        userAnswer = null
        super.submitList(list)
    }

    class PointViewHolder(
        private val binding: ViewAnswerBinding,
        private val onItemClicked: (answerIndex: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            answer: AnswerUIModel,
            correctAnswer: Int,
            userAnswer: Int?
        ) = with(binding) {
            root.setContent(answer)

            if (userAnswer != null && (correctAnswer == adapterPosition || userAnswer == adapterPosition)) {
                root.setStatus(userAnswer, correctAnswer, adapterPosition)
            } else {
                root.isNeutralL()
            }
            binding.root.setOnClickListener {
                if (userAnswer == null) {
                    onItemClicked(adapterPosition)
                }
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun onAnswerClick(userAnswer: Int) {
        onItemClicked(userAnswer)
        this.userAnswer = userAnswer

        notifyItemChanged(userAnswer)

        if (userAnswer != correctAnswer) {
            notifyItemChanged(correctAnswer!!)
        }
    }
}