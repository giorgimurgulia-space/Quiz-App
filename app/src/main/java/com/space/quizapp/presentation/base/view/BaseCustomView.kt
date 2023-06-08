package com.space.quizapp.presentation.base.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

abstract class BaseCustomView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    protected val paint by lazy { Paint() }
    protected val path by lazy { Path() }

    protected val width get() = getWidth().toFloat()
    protected val height get() = getHeight().toFloat()

    abstract fun startDrawing(canvas: Canvas)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        startDrawing(canvas)
    }
}