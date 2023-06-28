package com.space.quizapp.presentation.base.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.space.quizapp.R
import com.space.quizapp.presentation.home.ui.HomeFragmentDirections
import com.space.quizapp.presentation.model.DialogUIModel
import com.space.quizapp.presentation.navigation.QuizEvent
import com.space.quizapp.presentation.navigation.NavigationCommand


abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableLiveData<QuizEvent<NavigationCommand>>()
    val navigation: LiveData<QuizEvent<NavigationCommand>> get() = _navigation

    private val _dialog = MutableLiveData<QuizEvent<DialogUIModel>>()
    val dialog get() = _dialog

    private val _closeDialog = MutableLiveData<QuizEvent<Unit>>()
    val closeDialog get() = _closeDialog


    fun navigate(navDirections: NavDirections) {
        _navigation.value = QuizEvent(NavigationCommand.ToDirection(navDirections))
    }

    fun navigateBack() {
        _navigation.value = QuizEvent(NavigationCommand.Back)
    }

    fun setDialog(dialog: DialogUIModel) {
        _dialog.value = QuizEvent(dialog)
    }

    fun closeLoaderDialog() {
        _closeDialog.value = QuizEvent((Unit))
    }

    open fun logOut() {
        setDialog(DialogUIModel(title = R.string.want_log_out, yesButton = {
            navigate(HomeFragmentDirections.actionGlobalLogOut())
        }))
    }
}