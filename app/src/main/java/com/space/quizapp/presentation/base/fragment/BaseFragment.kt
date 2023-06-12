package com.space.quizapp.presentation.base.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.space.quizapp.R
import com.space.quizapp.common.navigation.NavigationCommand
import com.space.quizapp.common.observeNonNull
import com.space.quizapp.common.types.Inflater
import com.space.quizapp.presentation.base.viewModel.BaseViewModel


abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(private val inflate: Inflater<VB>) :
    Fragment() {

    //TODO
    abstract val viewModel: VM

    private var _binding: VB? = null
    val binding get() = _binding!!

    private val dialog by lazy { Dialog(requireContext()) }

    abstract fun onBind()
    open fun setObserves() {}
    open fun setListeners() {}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
        setObserves()
        setListeners()
        observeNavigation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigationCommand ->
                handleNavigation(navigationCommand)
            }
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions)
            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }

    //todo better dialog
    protected fun loader(isLoaded: Boolean = false) {
        dialog.setContentView(R.layout.dialog_loader)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        if (!isLoaded)
            dialog.show()
        else
            dialog.dismiss()
    }

    //todo better dialog
    protected fun showQuestionDialog(question: Int, onPositiveButtonClick: () -> Unit) {
        dialog.setContentView(R.layout.dialog_question)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        dialog.findViewById<TextView>(R.id.question_text).text = resources.getString(question)

        val yesButton = dialog.findViewById<Button>(R.id.yes_button)
        val noButton = dialog.findViewById<Button>(R.id.no_button)

        yesButton.setOnClickListener {
            dialog.dismiss()
            onPositiveButtonClick.invoke()
        }

        noButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    protected fun showMessageDialog(question: String, onCloseButtonClick: () -> Unit) {
        dialog.setContentView(R.layout.dialog_message)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

//        dialog.findViewById<TextView>(R.id.question_text).text = resources.getString(question)
        dialog.findViewById<TextView>(R.id.message_text).text = question

        val closeButton = dialog.findViewById<TextView>(R.id.close_button)

        closeButton.setOnClickListener {
            dialog.dismiss()
            onCloseButtonClick.invoke()
        }
        dialog.show()
    }
}