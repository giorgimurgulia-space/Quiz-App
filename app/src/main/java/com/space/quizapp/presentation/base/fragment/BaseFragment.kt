package com.space.quizapp.presentation.base.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.space.quizapp.R
import com.space.quizapp.common.observeNonNull
import com.space.quizapp.common.types.Inflater
import com.space.quizapp.presentation.base.vm.BaseViewModel
import com.space.quizapp.presentation.model.DialogUIModel
import com.space.quizapp.presentation.navigation.NavigationCommand
import com.space.quizapp.presentation.view.DialogView


abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(private val inflate: Inflater<VB>) :
    Fragment() {
    abstract val viewModel: VM

    private var _binding: VB? = null
    val binding get() = _binding!!

    private val alertDialog by lazy { DialogView(requireContext()) }
    private val quizDialog by lazy { DialogView(requireContext()) }

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

    private fun observeDialogStatus() {
        viewModel.dialogStatus.observeNonNull(viewLifecycleOwner) {
            if (it)
                showDialog()
            else
                closeDialog()
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions)
            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }

    protected fun loader(isLoaded: Boolean = false) {
        alertDialog.setContentView(R.layout.layout_dialog)

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(false)

        if (!isLoaded)
            alertDialog.show()
        else
            alertDialog.dismiss()
    }


    protected fun setDialogContent(dialog: DialogUIModel) {
        quizDialog.setContent(dialog)
    }

    protected fun showDialog() {
        quizDialog.show()
    }

    protected fun closeDialog() {
        quizDialog.dismiss()
    }
}