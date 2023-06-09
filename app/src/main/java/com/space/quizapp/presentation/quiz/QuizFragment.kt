package com.space.quizapp.presentation.quiz

import androidx.fragment.app.viewModels
import com.space.quizapp.databinding.FragmentQuizBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import com.space.quizapp.presentation.point.PointsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment :
    BaseFragment<FragmentQuizBinding, QuizViewModel>(FragmentQuizBinding::inflate) {
    override val viewModel: QuizViewModel by viewModels()

    override fun onBind() {
    }

}