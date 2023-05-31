package com.space.quizapp.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.space.quizapp.databinding.FragmentHomeBinding
import com.space.quizapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    override fun onBind() {
    }

    override fun observes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastMessage.collect { user ->
                    binding.welcomeTitleText.text = user.username
                    binding.GPAText.text = user.userGPA
                }
            }
        }
    }

    override fun listeners() {

    }
}