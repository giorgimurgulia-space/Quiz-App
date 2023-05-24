package com.space.quizapp.ui.start

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.space.quizapp.databinding.FragmentStartBinding
import com.space.quizapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {
    private val viewModel: StartViewModel by viewModels()

    override fun onBind() = with(binding) {

        startButton.setOnClickListener {
            val userName = nameEditText.text?.toString()
            viewModel.startButtonListener(userName)
        }
    }

    override fun observes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastMessage.collect { messages ->
                    toast(messages)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userId.collect { userId ->
                    binding.startTitleText.text = userId
                }
            }
        }
    }


}