package com.space.quizapp.presentation.quiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.space.quizapp.databinding.ViewAnswerBinding
import com.space.quizapp.presentation.model.AnswerUIModel

class AnswerAdapter : ListAdapter<AnswerUIModel, AnswerAdapter.PointViewHolder>(AnswerDiffUtil()) {

    var correctAnswer: Int? = null
    var userAnswer: Int? = null
    private var callBack: CallBack? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PointViewHolder {
        return PointViewHolder(
            ViewAnswerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            callBack
        )
    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }


    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(getItem(position), correctAnswer!!, userAnswer)
    }

    class PointViewHolder(
        private val binding: ViewAnswerBinding,
        private val callBack: CallBack?,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(answer: AnswerUIModel, correctAnswer: Int, userAnswer: Int?) = with(binding) {
            root.setContent(answer)

            if (userAnswer != null && (correctAnswer == adapterPosition || userAnswer == adapterPosition))
                root.setStatus(userAnswer, correctAnswer, adapterPosition)
            else
                root.isNeutralL()

            root.setOnClickListener {
                if (userAnswer == null)
                    callBack?.onItemClick(adapterPosition)
            }
        }
    }

    interface CallBack {
        fun onItemClick(answerIndex: Int)
    }
}