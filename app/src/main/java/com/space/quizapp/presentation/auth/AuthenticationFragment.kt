package com.space.quizapp.presentation.auth

import androidx.fragment.app.viewModels
import com.space.quizapp.common.extensions.collectFlow
import com.space.quizapp.databinding.FragmentAuthenticationBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationFragment :
    BaseFragment<FragmentAuthenticationBinding, AuthenticationViewModel>(
        FragmentAuthenticationBinding::inflate
    ) {
    override val viewModel: AuthenticationViewModel by viewModels()

    override fun onBind() = with(binding) {
        startButton.setOnClickListener {
            val username = nameEditText.text?.toString()
            viewModel.authButtonListener(username)
        }
    }

    override fun setObserves() {
        collectFlow(viewModel.toastMessage) { messages ->
            toast(resources.getString(messages))
        }
    }
}