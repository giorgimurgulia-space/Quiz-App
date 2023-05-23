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


    fun startButtonListener(name: String?) {
        if (name.isNullOrEmpty()) {
            _startMessage.tryEmit("გთხოვთ შიყვანოთ სახელი")
        }
    }
}