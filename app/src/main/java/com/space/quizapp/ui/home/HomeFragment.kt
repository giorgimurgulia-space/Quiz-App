package com.space.quizapp.ui.home

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView.BufferType
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.space.quizapp.R
import com.space.quizapp.common.resource.onError
import com.space.quizapp.common.resource.onLoading
import com.space.quizapp.common.resource.onSuccess
import com.space.quizapp.databinding.FragmentHomeBinding
import com.space.quizapp.ui.base.BaseFragment
import com.space.quizapp.ui.model.UserUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = QuizAdapter()


    override fun onBind() {
        binding.mainRecycler.layoutManager =
            LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter
    }

    override fun observes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    setUserData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.availableQuiz.collect {
                    it.onSuccess { quiz ->
                        adapter.submitList(quiz)
                        binding.loaderProgressBarr.visibility = View.GONE
                    }
                    it.onLoading {
                        binding.loaderProgressBarr.visibility = View.VISIBLE
                    }
                    it.onError {
                        binding.loaderProgressBarr.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun listeners() {

    }

    private fun setUserData(user: UserUIModel?) = with(binding) {
        if (user != null) {
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