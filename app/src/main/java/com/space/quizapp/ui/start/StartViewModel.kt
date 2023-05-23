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
            _startMessage.tryEmit("სახელის სტრუქტურა")
        } else {
            _startMessage.tryEmit(isStrongUserName(userName).toString())
            checkUser(userName)
        }
    }

    private fun checkUser(userName: String): String {
        return "user id"
    }

    private fun isStrongUserName(userName: String): Boolean {
        var check = true

        check = userName.length in 8..20

        if (check)
            check = !userName.startsWith(".")

        if (check)
            check = !userName.startsWith("_")

        if (check)
            check = !userName.contains(" ")

        if (check)
            check = userName != userName.lowercase()

        return check
    }


}