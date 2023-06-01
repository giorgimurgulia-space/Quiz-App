package com.space.quizapp.ui.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

// A base abstract class representing a custom view with basic drawing capabilities
abstract class QuizBaseCustomView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // The paint object used for drawing on the canvas
    protected open val paint = Paint().apply {
        isAntiAlias = true
    }
    // The path object used for drawing on the canvas
    protected val path = Path()
    // Returns the width of the custom view
    protected val width get() = getWidth().toFloat()
    //Returns the height of the custom view
    protected val height get() = getHeight().toFloat()


    // Binds the data to the custom view and performs drawing operations on the canvas
    abstract fun onBind(canvas: Canvas)

    // Called when the custom view needs to be drawn on the canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        onBind(canvas)
    }
}