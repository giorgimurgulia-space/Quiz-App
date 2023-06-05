package com.space.quizapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.quizapp.R
import com.space.quizapp.common.regex.RegexPattern
import com.space.quizapp.data.local.database.model.dao.UserPointDao
import com.space.quizapp.data.local.database.model.entity.UserPointEntity
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

    // change name
    private val _userId = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val userId get() = _userId.asSharedFlow()


    fun startButtonListener(username: String?) {
        //when
        if (username.isNullOrEmpty()) {
            _toastMessage.tryEmit(R.string.please_input_username)
        } else if (!isStrongUserName(username)) {
            _toastMessage.tryEmit(R.string.invalid_username)
        } else {
            startButtonProcess(username)
        }
    }

    //name
    private fun startButtonProcess(username: String) {
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
            //try
            _userId.tryEmit(Unit)
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

    private fun isStrongUserName(userName: String): Boolean {
        return RegexPattern.usernamePattern.matches(userName)
    }


}