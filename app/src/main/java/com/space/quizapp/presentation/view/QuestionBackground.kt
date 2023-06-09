package com.space.quizapp.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.space.quizapp.R
import com.space.quizapp.presentation.base.view.BaseCustomView

class QuestionBackground(
    context: Context,
    attributeSet: AttributeSet
) : BaseCustomView(context, attributeSet) {

    override fun startDrawing(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        drawMainBackground(canvas)
    }

    private val blueBackgroundColor =
        ContextCompat.getColor(context, R.color.blue_secondary_lightest)

    private fun drawMainBackground(canvas: Canvas) {
        val radius = width / 2
        path.apply {
            reset()
            paint.color = blueBackgroundColor
            val rightRectF = RectF(width - radius, height - radius, width, height)
            val leftRectF = RectF(0f, height - radius, radius, height)
            moveTo(0f, 0f)
            lineTo(width, 0f)
            arcTo(
                RectF(width - radius, height - radius, width, height),
                0f, 90f
            )
            arcTo(
                RectF(0f, height - radius, radius, height),
                90f, 90f
            )
            close()
        }
        canvas.drawPath(path, paint)
    }
}