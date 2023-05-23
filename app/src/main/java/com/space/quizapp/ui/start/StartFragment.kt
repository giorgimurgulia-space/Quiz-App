package com.space.quizapp.ui.start

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.space.quizapp.databinding.FragmentStartBinding
import com.space.quizapp.ui.BaseFragment
import kotlinx.coroutines.launch

class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {
    private val viewModel: StartViewModel by viewModels()

    override fun onBind() = with(binding) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.startMessage.collect { messages ->
                    toast(messages)
                }
            }
        }

        startButton.setOnClickListener {
            val userName = nameEditText.text?.toString()
            viewModel.startButtonListener(userName)
        }
    }

    override fun observes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.startMessage.collect { messages ->
                    toast(messages)
                }
            }
        }
    }


}