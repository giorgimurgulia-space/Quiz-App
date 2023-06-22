package com.space.quizapp.presentation.view

import android.content.Context
import android.content.res.ColorStateList
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
        when (position) {
            userAnswer -> {
                if (userAnswer == correctAnswer) {
                    binding.root.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.success_green))
                    binding.plusPintText.visibility = View.VISIBLE
                    binding.answerText.setTextColor(resources.getColor(R.color.neutral_04_white))
                } else {
                    binding.root.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.wrong_red))
                    binding.plusPintText.visibility = View.GONE
                    binding.answerText.setTextColor(resources.getColor(R.color.neutral_04_white))
                }
            }
            correctAnswer -> {
                binding.root.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.success_green))
                binding.plusPintText.visibility = View.GONE
                binding.answerText.setTextColor(resources.getColor(R.color.neutral_04_white))
            }
        }


    }

    fun isNeutralL() {
        binding.root.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.neutral_04_light_grey))
        binding.plusPintText.visibility = View.GONE
        binding.answerText.setTextColor(resources.getColor(R.color.neutral_01_dark_grey))

    }
}
