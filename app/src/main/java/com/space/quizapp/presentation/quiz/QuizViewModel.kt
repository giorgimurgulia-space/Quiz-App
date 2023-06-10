package com.space.quizapp.presentation.quiz

import com.space.quizapp.domain.usecase.auth.AuthenticationUseCase
import com.space.quizapp.presentation.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,

    ) : BaseViewModel() {


    fun startQuiz(subjectID: String) {

    }
}