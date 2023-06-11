package com.space.quizapp.presentation.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.space.quizapp.R
import com.space.quizapp.common.AnswerStatus
import com.space.quizapp.common.extensions.collectFlow
import com.space.quizapp.common.resource.onError
import com.space.quizapp.common.resource.onLoading
import com.space.quizapp.common.resource.onSuccess
import com.space.quizapp.databinding.FragmentQuizBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import com.space.quizapp.presentation.home.QuizAdapter
import com.space.quizapp.presentation.model.AnswerUIModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizFragment :
    BaseFragment<FragmentQuizBinding, QuizViewModel>(FragmentQuizBinding::inflate) {

    override val viewModel: QuizViewModel by viewModels()
    private val adapter = AnswerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(),R.color.blue_secondary_lightest)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onBind() {
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

        collectFlow(viewModel.quiz) {
            binding.titleText.text = it.quizTitle
        }

        collectFlow(viewModel.question) {
            binding.questionText.text = it.question
            changeSubmitButtonText(it.isLastQuestion)
        }

        collectFlow(viewModel.answers) {
            it.onSuccess { answers ->
                adapter.submitList(answers)
                loader(true)
            }
            it.onLoading {
                loader()
            }
            it.onError {
                loader(true)
                showQuestionDialog(R.string.error_message, onPositiveButtonClick = {
                    // todo
                })
            }
        }

        collectFlow(viewModel.point) {
            toast(it.toString())
        }
    }

    override fun setListeners() {
        binding.cancelImage.setOnClickListener {
            showQuestionDialog(R.string.cancel_quiz, onPositiveButtonClick = {
                viewModel.navigateBack()
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