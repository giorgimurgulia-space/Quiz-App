package com.space.quizapp.presentation.quiz.ui

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.space.quizapp.R
import com.space.quizapp.common.extensions.collectFlow
import com.space.quizapp.common.resource.onError
import com.space.quizapp.common.resource.onLoading
import com.space.quizapp.common.resource.onSuccess
import com.space.quizapp.databinding.FragmentQuizBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import com.space.quizapp.presentation.quiz.adapter.AnswerAdapter
import com.space.quizapp.presentation.quiz.vm.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizFragment :
    BaseFragment<FragmentQuizBinding, QuizViewModel>(FragmentQuizBinding::inflate) {

    override val viewModel: QuizViewModel by viewModels()
    private val adapter = AnswerAdapter()

    override fun onBind() {
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.blue_secondary_lightest)

        //todo
        val subjectId = arguments?.getString("subjectID")
        viewModel.startQuiz(subjectId!!)

        binding.mainRecycler.layoutManager =
            LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter

    }

    override fun setObserves() {
        adapter.setCallBack(object : AnswerAdapter.CallBack {
            override fun onItemClick(answerIndex: Int) {
                viewModel.onAnswerClick(answerIndex)
            }
        })

        collectFlow(viewModel.quizState) {
            binding.titleText.text = it.quizTitle

            binding.questionText.text = it.question
            changeSubmitButtonText(it.isLastQuestion)

            it.answers.onSuccess { answers ->
                adapter.submitList(answers)
                loader(true)
            }
            it.answers.onLoading {
                loader()
            }
            it.answers.onError {
                loader(true)
                showQuestionDialog(R.string.error_message, onPositiveButtonClick = {
                    // todo
                })
            }

            if (it.point != null) {
                showMessageDialog(it.point.toString(), onCloseButtonClick = {
                    viewModel.navigateBack()
                })
            }
        }
    }

    override fun setListeners() {
        binding.cancelImage.setOnClickListener {
            showQuestionDialog(R.string.cancel_quiz, onPositiveButtonClick = {
                viewModel.cancelQuiz()
            })
        }

        binding.submitButton.setOnClickListener {
            viewModel.onSubmitButtonClick()
        }
    }

    private fun changeSubmitButtonText(isLastQuestion: Boolean) {
        if (isLastQuestion)
            binding.submitButton.text = "დასრულება"
        else
            binding.submitButton.text = "შემდეგი"
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}