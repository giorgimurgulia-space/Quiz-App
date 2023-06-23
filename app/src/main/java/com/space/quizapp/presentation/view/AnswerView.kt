package com.space.quizapp.presentation.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.space.quizapp.R
import com.space.quizapp.databinding.LayoutAnswerBinding
import com.space.quizapp.presentation.model.AnswerUIModel

class AnswerView(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {
    private val binding = LayoutAnswerBinding.inflate(LayoutInflater.from(context), this, true)

    fun setContent(answer: AnswerUIModel) {
        with(binding) {
            answerText.text = answer.answerTitle
        }
    }

    fun setStatus(userAnswer: Int, correctAnswer: Int, position: Int) {
        changeTextColor(R.color.neutral_04_white)

        when (position) {
            userAnswer -> {
                if (userAnswer == correctAnswer) {
                    changeBackgroundColor(R.color.success_green)
                    showPoint(true)
                } else {
                    changeBackgroundColor(R.color.wrong_red)
                    showPoint(false)
                }
            }
            correctAnswer -> {
                changeBackgroundColor(R.color.success_green)
                showPoint(false)
            }
        }


    }

    fun isNeutralL() {
        changeBackgroundColor(R.color.neutral_04_light_grey)
        changeTextColor(R.color.neutral_01_dark_grey)
        binding.plusPintText.visibility = View.GONE
    }

    private fun changeBackgroundColor(color: Int) {
        binding.root.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(color))
    }

    private fun showPoint(isVisible: Boolean) {
        binding.plusPintText.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun changeTextColor(color: Int) {
        binding.answerText.setTextColor(resources.getColor(color))
    }
}
