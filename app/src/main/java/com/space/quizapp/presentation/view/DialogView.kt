package com.space.quizapp.presentation.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.space.quizapp.databinding.LayoutDialogBinding
import com.space.quizapp.presentation.model.DialogUIModel

class DialogView(context: Context) : Dialog(context) {

    val binding = LayoutDialogBinding.inflate(LayoutInflater.from(context))

    private var dialog: DialogUIModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        setCanceledOnTouchOutside (false)

        dialog?.let {

        }

    }


    fun setContent(dialog: DialogUIModel): DialogView {
        showIcon(dialog.icon)
        setTitle(dialog.title)
        setDescription(dialog.description)
        setYesNoButton(dialog.yesButton)
        setCloseButton(dialog.closeButton)
        setProgressBar(dialog.isProgressbar)

        setContentView(binding.root)
        this.dialog = dialog
        return this
    }


    private fun showIcon(isVisible: Boolean) {
        binding.iconText.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun setTitle(title: Int?) {
        if (title != null)
            binding.titleText.text = context.resources.getString(title)
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

    private fun setProgressBar(isProgressBar: Boolean) {
        if (isProgressBar) {
            binding.loaderProgressBarr.visibility = View.VISIBLE
            binding.loaderText.visibility = View.VISIBLE
        } else {
            binding.loaderProgressBarr.visibility = View.GONE
            binding.loaderText.visibility = View.GONE
        }
    }
}