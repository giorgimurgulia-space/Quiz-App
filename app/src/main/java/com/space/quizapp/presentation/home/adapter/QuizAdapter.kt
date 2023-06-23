package com.space.quizapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.space.quizapp.common.extensions.loadImage
import com.space.quizapp.databinding.LayoutQuizBinding
import com.space.quizapp.presentation.model.AvailableQuizUIModel

class QuizAdapter : ListAdapter<AvailableQuizUIModel, QuizAdapter.QuizViewHolder>(QuizDiffUtil()) {

    private var callBack: CallBack? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizViewHolder {
        return QuizViewHolder(
            LayoutQuizBinding.inflate(
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

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class QuizViewHolder(
        private val binding: LayoutQuizBinding,
        private val callBack: CallBack?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(quiz: AvailableQuizUIModel) = with(binding) {
            titleText.text = quiz.quizTitle
            descriptionText.text = quiz.quizDescription

            iconImage.loadImage(quiz.quizIcon)

            root.setOnClickListener {
                callBack?.onItemClick(quiz.id)
            }
        }
    }

    interface CallBack {
        fun onItemClick(subjectId: String)
    }
}