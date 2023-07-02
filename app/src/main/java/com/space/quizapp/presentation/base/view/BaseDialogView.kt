package com.space.quizapp.presentation.base.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.viewbinding.ViewBinding

abstract class BaseDialogView(context: Context) : Dialog(context) {

    abstract val binding: ViewBinding

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCancelable(false)
        this.setCanceledOnTouchOutside(false)
    }
}