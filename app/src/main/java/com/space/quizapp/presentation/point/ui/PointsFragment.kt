package com.space.quizapp.presentation.point.ui

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.space.quizapp.R
import com.space.quizapp.common.extensions.collectFlow
import com.space.quizapp.common.resource.onError
import com.space.quizapp.common.resource.onLoading
import com.space.quizapp.common.resource.onSuccess
import com.space.quizapp.databinding.FragmentPointsBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import com.space.quizapp.presentation.model.DialogUIModel
import com.space.quizapp.presentation.point.adapter.PointAdapter
import com.space.quizapp.presentation.point.vm.PointsViewModel
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
        binding.backImage.setOnClickListener {
            viewModel.navigateBack()
        }
        binding.logOutButton.setOnClickListener {
//            setDialogContent(
//                DialogUIModel(title = R.string.want_log_out, yesButton = {
//                    viewModel.logOut()
//                })
//            )
        }
    }

    override fun setObserves() {
        collectFlow(viewModel.points) {
            //todo base
            it.onSuccess { quiz ->
                adapter.submitList(quiz)
            }
//            it.onLoading {
//                loader()
//            }
//            it.onError {
//                loader(true)
//                setDialogContent(
//                    DialogUIModel(
//                        title = R.string.error_message,
//                        yesButton = {
//                            viewModel.refreshAllData()
//                        }
//                    ))
//            }
        }
    }
}