package com.space.quizapp.presentation.quiz

import androidx.fragment.app.viewModels
import com.space.quizapp.R
import com.space.quizapp.databinding.FragmentQuizBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizFragment :
    BaseFragment<FragmentQuizBinding, QuizViewModel>(FragmentQuizBinding::inflate) {
    override val viewModel: QuizViewModel by viewModels()

    override fun onBind() {
        //todo
        val subjectId = arguments?.getString("subjectID")
        binding.titleText.text = subjectId.toString()
    }

    override fun setListeners() {
        binding.cancelImage.setOnClickListener {
            showQuestionDialog(R.string.cancel_quiz, onPositiveButtonClick = {
                viewModel.navigateBack()
            })
        }
    }

}