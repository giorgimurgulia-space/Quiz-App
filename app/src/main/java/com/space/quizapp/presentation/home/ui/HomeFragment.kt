package com.space.quizapp.presentation.home.ui

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.widget.TextView.BufferType
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.space.quizapp.R
import com.space.quizapp.common.extensions.collectFlow
import com.space.quizapp.common.resource.onError
import com.space.quizapp.common.resource.onLoading
import com.space.quizapp.common.resource.onSuccess
import com.space.quizapp.databinding.FragmentHomeBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import com.space.quizapp.presentation.home.vm.HomeViewModel
import com.space.quizapp.presentation.home.adapter.QuizAdapter
import com.space.quizapp.presentation.model.DialogUIModel
import com.space.quizapp.presentation.model.UserUIModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeViewModel by viewModels()
    private val adapter = QuizAdapter()

    override fun onBind() {
        viewModel.refreshAllData(true)

        binding.mainRecycler.layoutManager =
            LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter

        adapter.setCallBack(object : QuizAdapter.CallBack {
            override fun onItemClick(subjectId: String) {
                viewModel.onQuizClick(subjectId)
            }
        })
    }

    override fun setObserves() {
        collectFlow(viewModel.state) {
            setUserData(it)
        }

        collectFlow(viewModel.availableQuiz) {
            adapter.submitList(it)
        }
    }

    override fun setListeners() {
        binding.logOutButton.setOnClickListener {
            viewModel.setDialog(
                DialogUIModel(title = R.string.want_log_out, yesButton = {
                    viewModel.logOut()
                })
            )
        }

        binding.gpaBackgroundView.setOnClickListener {
            viewModel.navigateToPointsPage()
        }

        binding.root.setOnRefreshListener {
            viewModel.refreshAllData()
            binding.root.isRefreshing = false
        }
    }

    private fun setUserData(user: UserUIModel) = with(binding) {
        if (user.username != null) {
            val userGPA = String.format(resources.getString(R.string.user_GPA), user.userGPA)

            helloTitleText.text =
                String.format(resources.getString(R.string.welcome_messages), user.username)

            userGpaText.setText(userGPA, BufferType.SPANNABLE)
            val span = userGpaText.text as Spannable
            span.setSpan(
                ForegroundColorSpan(requireContext().getColor(R.color.yellow_primary)),
                userGPA.length - user.userGPA.length,
                userGPA.length,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    }
}