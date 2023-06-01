package com.space.quizapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.quizapp.data.remote.api.ApiService
import com.space.quizapp.domain.usecase.quiz.QuizUseCase
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
    private val userDataUseCse: UserDataUseCse,
    private val quizUseCase: QuizUseCase,
    private val apiService: ApiService

) : ViewModel() {

    private val _toastMessage = MutableSharedFlow<UserUIModel>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val toastMessage get() = _toastMessage.asSharedFlow()


    init {
        viewModelScope.launch {
            val user = userDataUseCse.getCurrentUser()
            val userGPA = userDataUseCse.getCurrentUserGPA()

            _toastMessage.tryEmit(UserUIModel(user.userId, user.username, userGPA))

            val response = apiService.getQuiz()
            if (response.isSuccessful) {
                val quiz = response.body()
                _toastMessage.tryEmit(UserUIModel(user.userId, quiz!![0].quizTitle, userGPA))

            }

        }
    }

}