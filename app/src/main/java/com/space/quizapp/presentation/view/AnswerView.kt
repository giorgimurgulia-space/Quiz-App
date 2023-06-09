package com.space.quizapp.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.space.quizapp.databinding.LayoutAnswerBinding
import com.space.quizapp.presentation.model.PointUIModel

class AnswerView(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {
    private val binding = LayoutAnswerBinding.inflate(LayoutInflater.from(context), this, true)

    fun setContent(point: PointUIModel) {
        with(binding) {
        }
    }
}