package com.space.quizapp.presentation.base.fragment

import android.app.Dialog
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
import com.space.quizapp.presentation.base.view.BaseDialogView
import com.space.quizapp.presentation.base.vm.BaseViewModel
import com.space.quizapp.presentation.model.DialogItem
import com.space.quizapp.presentation.navigation.NavigationCommand
import com.space.quizapp.presentation.view.DialogNotificationView
import com.space.quizapp.presentation.view.DialogQuestionView


abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(private val inflate: Inflater<VB>) :
    Fragment() {
    abstract val viewModel: VM

    private var _binding: VB? = null
    val binding get() = _binding!!

    protected lateinit var quizDialog: Dialog

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

    private fun observeDialog() {
        viewModel.dialog.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { dialog ->
                when (dialog.viewType) {
                    DialogItem.ViewType.LOADER -> {
                        dialog as DialogItem.LoaderDialog
                        if (dialog.isProgressbar) {
                            quizDialog = Dialog(requireContext())
                            quizDialog.setContentView(R.layout.layout_loader_dialog)
                            quizDialog.show()
                        } else
                            quizDialog.dismiss()
                    }
                    DialogItem.ViewType.QUESTION -> {
                        dialog as DialogItem.QuestionDialog
                        DialogQuestionView(requireContext()).setContent(dialog).show()
                    }
                    DialogItem.ViewType.NOTIFICATION -> {
                        dialog as DialogItem.NotificationDialog
                        DialogNotificationView(requireContext()).setContent(dialog)
                            .show()
                    }
                }
            }
        }
    }

    protected fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}