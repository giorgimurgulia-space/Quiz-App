package com.space.quizapp.presentation.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.space.quizapp.R
import com.space.quizapp.presentation.base.QuizBaseCustomView


class StartIllustrationBackground @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : QuizBaseCustomView(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    fun paint() {

    }

    private val blueColor = ContextCompat.getColor(context, R.color.blue_secondary_default)
    private val blueFadeColor = ContextCompat.getColor(context, R.color.blue_secondary_light)


    private fun drawVector(canvas: Canvas, width: Float, height: Float) {
        rectanglePath.apply {
            paint.color = blueColor
            moveTo(0f, height/2)
            moveTo(0f, 0f)
            arcTo(
                RectF(0f, 0f, width, height),
                180f, 90f
            )
            close()
        }
        canvas.drawPath(rectanglePath, paint)
    }

    private fun drawShade(canvas: Canvas, width: Float, height: Float) {
        shadePath.apply {
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
        canvas.drawPath(shadePath, paint)
    }

    override fun startDrawing(canvas: Canvas, width: Float, height: Float) {
        drawVector(canvas, width, height)
        drawShade(canvas, width, height)
    }
}