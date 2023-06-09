package com.space.quizapp.presentation.point

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.space.quizapp.databinding.ViewQuizBinding
import com.space.quizapp.presentation.model.PointUIModel

class PointAdapter : ListAdapter<PointUIModel, PointAdapter.PointViewHolder>(PointDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PointViewHolder {
        return PointViewHolder(
            ViewQuizBinding.inflate(
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
        private val binding: ViewQuizBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(point: PointUIModel) = with(binding) {
            root.setContent(point)
        }

    }
}