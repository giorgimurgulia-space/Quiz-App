package com.space.quizapp.presentation.base.view

import android.app.Dialog
import android.content.Context
import androidx.viewbinding.ViewBinding

abstract class QuizDialogView(context: Context) : Dialog(context) {
    abstract val binding: ViewBinding
    protected val alertDialog by lazy { Dialog(context) }
}