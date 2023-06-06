package com.space.quizapp.presentation.auth

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.space.quizapp.databinding.FragmentAuthenticationBinding
import com.space.quizapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthenticationFragment :
    BaseFragment<FragmentAuthenticationBinding>(FragmentAuthenticationBinding::inflate) {
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onBind() = with(binding) {
        illustrationBkgView.paint()
        startButton.setOnClickListener {
            val username = nameEditText.text?.toString()
            viewModel.authButtonListener(username)
        }
    }

    override fun observes() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.successNavigation.collect {
                    findNavController().navigate(AuthenticationFragmentDirections.actionGlobalHomeFragment())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastMessage.collect { messages ->
                    toast(resources.getString(messages))
                }
            }
        }
    }


}