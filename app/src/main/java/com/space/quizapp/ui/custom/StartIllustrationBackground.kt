package com.space.quizapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import com.space.quizapp.R
import com.space.quizapp.ui.base.QuizBaseCustomView

class StartIllustrationBackground(context: Context, attrs: AttributeSet) :
    QuizBaseCustomView(context, attrs) {

    override val paint = Paint().apply {
        style = Paint.Style.FILL
    }

    private val centerX get() = width / 2
    private val radius get() = width / 2

    /**[drawBackground] Draws the background of the custom view on the canvas
     */
    override fun drawBackground(canvas: Canvas) {
        //The height difference used to position the circles vertically.
        val heightDiff = (width / 2) - (height / 3)
        //The y-coordinate of the center for the first circle.
        val centerY1 = height / 3 + heightDiff
        //The y-coordinate of the center for the second circle.
        val centerY2 = height * 2 / 3 - heightDiff

        path.apply {
            reset()
            paint.color = context.getColor(R.color.blue_secondary_light)
            addCircle(centerX, centerY1, radius, Path.Direction.CW)
            addCircle(centerX, centerY2, radius, Path.Direction.CW)

            moveTo(centerX, 0f)
            lineTo(width, 0f)
            lineTo(width, centerY2)
            lineTo(centerX, height)
            lineTo(0f, height)
            lineTo(0f, centerY1)
            lineTo(centerX, 0f)
            close()
            canvas.drawPath(path, paint)
        }
    }

    // Draws the corner shape on the canvas
    private fun drawCorner(canvas: Canvas) {
        path.apply {
            reset()
            paint.color = context.getColor(R.color.blue_secondary_default)
            moveTo(0f, 0f)
            lineTo(centerX, 0f)
            lineTo(0f, height / 2)
            close()
            canvas.drawPath(path, paint)
        }
    }

    override fun onBind(canvas: Canvas) {
        drawCorner(canvas)
        drawBackground(canvas)
    }
}