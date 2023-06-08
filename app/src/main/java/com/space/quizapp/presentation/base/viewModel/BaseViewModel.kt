package com.space.quizapp.presentation.base.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.space.quizapp.common.QuizEvent
import com.space.quizapp.common.navigation.NavigationCommand


abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableLiveData<QuizEvent<NavigationCommand>>()
    val navigation: LiveData<QuizEvent<NavigationCommand>> get() = _navigation

    fun navigate(navDirections: NavDirections) {
        _navigation.value = QuizEvent(NavigationCommand.ToDirection(navDirections))
    }

    fun navigateBack() {
        _navigation.value = QuizEvent(NavigationCommand.Back)
    }

}