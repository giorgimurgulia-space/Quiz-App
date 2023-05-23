package com.space.quizapp.ui.start

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class StartViewModel : ViewModel() {
    private val _startMessage = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val startMessage get() = _startMessage.asSharedFlow()


    fun startButtonListener(userName: String?) {
        if (userName.isNullOrEmpty()) {
            _startMessage.tryEmit("გთხოვთ შიყვანოთ სახელი")
        } else if (!isStrongUserName(userName)) {
            _startMessage.tryEmit("სახელის სტრუქტურა არასწორია")
        } else {
            _startMessage.tryEmit(isStrongUserName(userName).toString())
            checkUser(userName)
        }
    }

    private fun checkUser(userName: String): String {
        return "user id"
    }

    private fun isStrongUserName(userName: String): Boolean {

        // regexPattern : minimum of 8 and maximum of 20 characters
        // containing at least one uppercase letter and one number
        // does not start with a period (.) or an underscore (_)

        val regexPattern = "^(?!\\.|_)(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$".toRegex()
        return regexPattern.matches(userName)
    }


}