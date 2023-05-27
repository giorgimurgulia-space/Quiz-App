package com.space.quizapp.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.quizapp.domain.usecase.start.AuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val checkUser: AuthenticationUseCase
) : ViewModel() {
    private val _toastMessage = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val toastMessage get() = _toastMessage.asSharedFlow()

    private val _userId = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val userId get() = _userId.asSharedFlow()


    fun startButtonListener(username: String?) {
        if (username.isNullOrEmpty()) {
            // string
            _toastMessage.tryEmit("გთხოვთ შეიყავნოთ სახელი")
        } else if (!isStrongUserName(username)) {
            _toastMessage.tryEmit("სახელის სტრუქტურა არასწორია")
        } else {
            startButtonProcess(username)
        }
    }

    private fun startButtonProcess(username: String) {
        viewModelScope.launch {
            if (checkUser.checkUser(username)) {
                sigInUser(username)
            } else {
                signUpUser(username)
            }
        }
    }

    private suspend fun sigInUser(username: String) {
        val useId = checkUser.getUserId(username)
        if (checkUser.signInUser(useId)) {
            _userId.tryEmit(true)
        } else {
            _toastMessage.tryEmit("გთხოვთ კიდევ სცადეთ")
        }
    }

    private suspend fun signUpUser(username: String) {
        checkUser.signUpUser(username)
        sigInUser(username)
    }

    private fun isStrongUserName(userName: String): Boolean {

        // regexPattern : minimum of 8 and maximum of 20 characters
        // containing at least one uppercase letter and one number
        // does not start with a period "." or an underscore "_"

        // regx class
        val regexPattern = "^(?!\\.|_)(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$".toRegex()
        return regexPattern.matches(userName)
    }


}