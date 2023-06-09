package com.space.quizapp.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.space.quizapp.common.extensions.loadImage
import com.space.quizapp.databinding.LayoutQuizBinding
import com.space.quizapp.presentation.model.PointUIModel

class QuizView(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {
    private val binding = LayoutQuizBinding.inflate(LayoutInflater.from(context), this, true)

    fun setContent(point: PointUIModel) {
        with(binding) {
            iconImage.loadImage(point.quizIcon)
            titleText.text = point.quizTitle
            descriptionText.text = point.quizDescription
            if (point.point.toString().toIntOrNull() != null)
                pointText.text = point.point.toInt().toString()
            else
                pointText.text = point.point.toString()

            nextImage.visibility = View.GONE
        }
    }
}