package com.space.quizapp.presentation.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.space.quizapp.R
import com.space.quizapp.presentation.base.view.BaseCustomView


class AuthIllustrationBackground(
    context: Context,
    attributeSet: AttributeSet
) : BaseCustomView(context, attributeSet) {

    override fun startDrawing(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        drawCornerMoon(canvas)
        drawMainBackground(canvas)
    }

    private var attributeArray: TypedArray? = context.theme.obtainStyledAttributes(
        attributeSet,
        R.styleable.AuthIllustrationBackground, 0, 0
    )

    //    private val blueCornerColor = ContextCompat.getColor(context, R.color.blue_secondary_default)
    private val blueCornerColor =
        attributeArray?.getColor(
            R.styleable.AuthIllustrationBackground_cornerColor,
            ContextCompat.getColor(context, R.color.neutral_04_white)
        ) ?: android.R.color.white

    //    private val blueBackgroundColor = ContextCompat.getColor(context, R.color.blue_secondary_light)
    private val blueBackgroundColor =
        attributeArray?.getColor(
            R.styleable.AuthIllustrationBackground_backgroundColor,
            ContextCompat.getColor(context, R.color.neutral_01_dark_grey)
        ) ?: android.R.color.black


    private fun drawCornerMoon(canvas: Canvas) {
        path.apply {
            reset()
            paint.color = blueCornerColor
            moveTo(0f, height / 2)
            moveTo(0f, 0f)
            arcTo(
                RectF(0f, 0f, width, height),
                180f, 90f
            )
            close()
        }
        canvas.drawPath(path, paint)
    }

    private fun drawMainBackground(canvas: Canvas) {
        path.apply {
            reset()
            paint.color = blueBackgroundColor
            moveTo(width, 0f)
            arcTo(
                RectF(0f, 0f, width, height),
                0f, 90f
            )
            lineTo(0f, height)
            arcTo(
                RectF(0f, 0f, width, height),
                180f, 90f
            )
            close()
        }
        canvas.drawPath(path, paint)
    }
}