package com.space.quizapp.presentation.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.space.quizapp.R
import com.space.quizapp.presentation.base.view.BaseCustomView


//todo transfer color
class AuthIllustrationBackground(
    context: Context,
    attributeSet: AttributeSet
) : BaseCustomView(context, attributeSet) {

    override fun startDrawing(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        drawCornerMoon(canvas)
        drawMainBackground(canvas)
    }

    private val blueColor = ContextCompat.getColor(context, R.color.blue_secondary_default)
    private val blueFadeColor = ContextCompat.getColor(context, R.color.blue_secondary_light)


    private fun drawCornerMoon(canvas: Canvas) {
        path.apply {
            reset()
            paint.color = blueColor
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
            paint.color = blueFadeColor
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