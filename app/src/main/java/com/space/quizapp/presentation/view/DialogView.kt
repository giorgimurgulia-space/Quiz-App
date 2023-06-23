package com.space.quizapp.presentation.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.space.quizapp.databinding.LayoutDialogBinding
import com.space.quizapp.presentation.model.DialogUIModel

class DialogView(
    context: Context,
) : FrameLayout(context) {
    private val binding = LayoutDialogBinding.inflate(LayoutInflater.from(context), this, true)

    private val alertDialog: AlertDialog by lazy { AlertDialog.Builder(context).create() }

    fun setContent(dialog: DialogUIModel): DialogView {
        showIcon(dialog.icon)
        setTitle(dialog.title)
        setDescription(dialog.description)
        setYesNoButton(dialog.yesButton)
        setCloseButton(dialog.closeButton)

        alertDialog.setView(this)

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(false)

        return this
    }

    fun show(): DialogView {
        alertDialog.show()
        return this
    }

    private fun dismiss() {
        alertDialog.dismiss()
    }

    private fun showIcon(isVisible: Boolean) {
        binding.iconText.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun setTitle(title: Int?) {
        if (title != null)
            binding.titleText.text = resources.getString(title)
        else
            binding.titleText.visibility = View.GONE
    }

    private fun setDescription(description: String?) {
        if (description != null)
            binding.descriptionText.text = description
        else
            binding.descriptionText.visibility = View.GONE
    }

    private fun setYesNoButton(onYesButton: (() -> Unit)?) {
        if (onYesButton != null) {
            binding.yesButton.setOnClickListener {
                onYesButton.invoke()
                dismiss()
            }
            binding.noButton.setOnClickListener {
                dismiss()
            }
        } else {
            binding.yesButton.visibility = View.GONE
            binding.noButton.visibility = View.GONE
        }
    }

    private fun setCloseButton(onCloseButton: (() -> Unit)?) {
        if (onCloseButton != null) {
            binding.closeButton.setOnClickListener {
                onCloseButton()
                dismiss()
            }
        } else {
            binding.lineView.visibility = View.GONE
            binding.closeButton.visibility = View.GONE
        }
    }


}
