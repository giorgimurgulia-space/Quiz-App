package com.space.quizapp.presentation.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.space.quizapp.databinding.ViewAnswerBinding
import com.space.quizapp.presentation.model.AnswerUIModel

class AnswerAdapter : ListAdapter<AnswerUIModel, AnswerAdapter.PointViewHolder>(AnswerDiffUtil()) {

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
        holder.bind(getItem(position))
    }

    class PointViewHolder(
        private val binding: ViewAnswerBinding,
        private val callBack: CallBack?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(answer: AnswerUIModel) = with(binding) {
            root.setContent(answer)

            root.setOnClickListener {
                onItemClick()
            }
        }

        private fun onItemClick() {
            callBack?.onItemClick(this.adapterPosition)
        }
    }

    interface CallBack {
        fun onItemClick(answerIndex: Int)
    }
}