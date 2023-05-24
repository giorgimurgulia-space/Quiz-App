package com.space.quizapp.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.quizapp.domain.usecase.start.StartUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val startUserUseCase: StartUserUseCase
) : ViewModel() {
    private val _toastMessage = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val toastMessage get() = _toastMessage.asSharedFlow()

    private val _userId = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val userId get() = _userId.asSharedFlow()


    fun startButtonListener(userName: String?) {
        if (userName.isNullOrEmpty()) {
            _toastMessage.tryEmit("გთხოვთ  სახელი")
        } else if (!isStrongUserName(userName)) {
            _toastMessage.tryEmit("სახელის სტრუქტურა არასწორია")
        } else {
            getUser(userName)
        }
    }

    private fun getUser(userName: String) {

        viewModelScope.launch {
            _userId.tryEmit(startUserUseCase.invoke(userName).userId)
            _toastMessage.tryEmit(startUserUseCase.invoke(userName).userId)
        }
    }

    private fun isStrongUserName(userName: String): Boolean {

        // regexPattern : minimum of 8 and maximum of 20 characters
        // containing at least one uppercase letter and one number
        // does not start with a period "." or an underscore "_"

        val regexPattern = "^(?!\\.|_)(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$".toRegex()
        return regexPattern.matches(userName)
    }


}