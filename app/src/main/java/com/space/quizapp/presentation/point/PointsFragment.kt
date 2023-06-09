package com.space.quizapp.presentation.point

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.space.quizapp.R
import com.space.quizapp.common.extensions.collectFlow
import com.space.quizapp.common.resource.onError
import com.space.quizapp.common.resource.onLoading
import com.space.quizapp.common.resource.onSuccess
import com.space.quizapp.databinding.FragmentPointsBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PointsFragment :
    BaseFragment<FragmentPointsBinding, PointsViewModel>(FragmentPointsBinding::inflate) {

    override val viewModel: PointsViewModel by viewModels()
    private val adapter = PointAdapter()

    override fun onBind() {
        binding.mainRecycler.layoutManager =
            LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter
    }

    override fun setListeners() {
        binding.logOutButton.setOnClickListener {
            viewModel.navigateBack()
        }
    }

    override fun setObserves() {
        collectFlow(viewModel.points) {
            //todo base
            it.onSuccess { quiz ->
                adapter.submitList(quiz)
                loader(false)
            }
            it.onLoading {
                loader(true)
            }
            it.onError {
                loader(true)
                showQuestionDialog(R.string.error_message, onPositiveButtonClick = {
                    viewModel.refreshAllData()
                })
            }
        }
    }
}