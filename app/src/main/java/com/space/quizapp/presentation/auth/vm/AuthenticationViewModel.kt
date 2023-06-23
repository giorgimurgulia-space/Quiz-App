package com.space.quizapp.presentation.auth.vm

import androidx.lifecycle.viewModelScope
import com.space.quizapp.R
import com.space.quizapp.common.regex.RegexPattern
import com.space.quizapp.domain.usecase.auth.AuthenticationUseCase
import com.space.quizapp.presentation.auth.ui.AuthenticationFragmentDirections
import com.space.quizapp.presentation.base.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
) : BaseViewModel() {
    private val _toastMessage = MutableSharedFlow<Int>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val toastMessage get() = _toastMessage.asSharedFlow()

    fun authButtonListener(username: String?) {
        when {
            username.isNullOrEmpty() -> {
                _toastMessage.tryEmit(R.string.please_input_username)
            }
            !isValidUsername(username) -> {
                //transfer to dialog
                _toastMessage.tryEmit(R.string.invalid_username)
            }
            else -> {
                authentication(username)
            }
        }
    }

    private fun authentication(username: String) {
        viewModelScope.launch {
            if (authenticationUseCase.invoke(username))
                navigate(AuthenticationFragmentDirections.actionGlobalHomeFragment())
            else
                _toastMessage.tryEmit(R.string.try_again)
        }
    }

    private fun isValidUsername(userName: String): Boolean {
        return RegexPattern.usernamePattern.matches(userName)
    }
}