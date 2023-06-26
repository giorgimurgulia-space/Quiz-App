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
import com.space.quizapp.common.extensions.observeNonNull
import com.space.quizapp.common.types.Inflater
import com.space.quizapp.presentation.base.vm.BaseViewModel
import com.space.quizapp.presentation.model.DialogUIModel
import com.space.quizapp.presentation.navigation.NavigationCommand
import com.space.quizapp.presentation.view.DialogView
import com.space.quizapp.presentation.view._DialogView


abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(private val inflate: Inflater<VB>) :
    Fragment() {
    abstract val viewModel: VM

    private var _binding: VB? = null
    val binding get() = _binding!!

    private val alertDialog by lazy { _DialogView(requireContext()) }

    private lateinit var quizDialog: DialogView

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
        observeDialog()
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

    private fun observeDialog() {
        viewModel.dialog.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { dialog ->
//                quizDialog.setContent(dialog).show()
                quizDialog = DialogView(requireContext()).setContent(dialog)
                quizDialog.show()
//                DialogView(requireContext()).setContent(dialog).show()
            }
        }
        viewModel.closeDialog.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                quizDialog.dismiss()
            }
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

    private fun showDialog() {
        quizDialog.show()
    }

    private fun closeDialog() {
        quizDialog.dismiss()
    }
}