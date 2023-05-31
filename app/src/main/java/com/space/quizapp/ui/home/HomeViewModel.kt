package com.space.quizapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.quizapp.common.mapper.toUIModel
import com.space.quizapp.domain.model.UserPoint
import com.space.quizapp.domain.usecase.user.UserDataUseCse
import com.space.quizapp.ui.model.UserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataUseCse: UserDataUseCse
) : ViewModel() {

    private val _toastMessage = MutableSharedFlow<UserUIModel>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val toastMessage get() = _toastMessage.asSharedFlow()


    init {
        viewModelScope.launch {
        }
    }

}