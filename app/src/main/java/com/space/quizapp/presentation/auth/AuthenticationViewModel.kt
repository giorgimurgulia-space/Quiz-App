package com.space.quizapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.quizapp.R
import com.space.quizapp.common.regex.RegexPattern
import com.space.quizapp.domain.usecase.auth.AuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
) : ViewModel() {
    private val _toastMessage = MutableSharedFlow<Int>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val toastMessage get() = _toastMessage.asSharedFlow()

    private val _successNavigation = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val successNavigation get() = _successNavigation.asSharedFlow()


    fun authButtonListener(username: String?) {
        when {
            username.isNullOrEmpty() -> {
                _toastMessage.tryEmit(R.string.please_input_username)
            }
            !isValidUsername(username) -> {
                _toastMessage.tryEmit(R.string.invalid_username)
            }
            else -> {
                checkAndAuthUser(username)
            }
        }
    }

    private fun checkAndAuthUser(username: String) {
        viewModelScope.launch {
            if (authenticationUseCase.checkUser(username)) {
                sigInUser(username)
            } else {
                signUpUser(username)
            }
        }
    }

    private suspend fun sigInUser(username: String) {
        if (authenticationUseCase.signInUser(username)) {
            //try??
            _successNavigation.tryEmit(Unit)
        } else {
            _toastMessage.tryEmit(R.string.try_again)
        }
    }

    private suspend fun signUpUser(username: String) {
        if (authenticationUseCase.signUpUser(username)) {
            sigInUser(username)
        } else {
            _toastMessage.tryEmit(R.string.try_again)
        }
    }

    private fun isValidUsername(userName: String): Boolean {
        return RegexPattern.usernamePattern.matches(userName)
    }
}