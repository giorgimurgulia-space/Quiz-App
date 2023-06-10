package com.space.quizapp.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.space.quizapp.R
import com.space.quizapp.common.AnswerStatus
import com.space.quizapp.databinding.LayoutAnswerBinding
import com.space.quizapp.presentation.model.AnswerUIModel

class AnswerView(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {
    private val binding = LayoutAnswerBinding.inflate(LayoutInflater.from(context), this, true)

    @SuppressLint("ResourceAsColor")
    fun setContent(answer: AnswerUIModel) {
        with(binding) {
            answerText.text = answer.answerTitle

            if (answer.answerStatus == AnswerStatus.CORRECT) {
                plusPintText.visibility = View.VISIBLE
            } else {
                plusPintText.visibility = View.GONE
            }

            when (answer.answerStatus) {
                AnswerStatus.CORRECT -> {
                    ColorStateList.valueOf(resources.getColor(R.color.success_green))
                }
                AnswerStatus.NEGATIVE -> {
                    ColorStateList.valueOf(resources.getColor(R.color.wrong_red))
                }
                AnswerStatus.POSITIVE -> {
                    ColorStateList.valueOf(resources.getColor(R.color.success_green))

                }
                else -> {

                }
            }
        }
    }
}
