package com.space.quizapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.quizapp.common.extensions.toResult
import com.space.quizapp.common.mapper.toUIModel
import com.space.quizapp.common.resource.Result
import com.space.quizapp.domain.usecase.auth.AuthenticationUseCase
import com.space.quizapp.domain.usecase.quiz.QuizUseCase
import com.space.quizapp.domain.usecase.user.UserDataUseCse
import com.space.quizapp.ui.model.QuizUIModel
import com.space.quizapp.ui.model.UserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataUseCse: UserDataUseCse,
    private val authenticationUseCase: AuthenticationUseCase,
    private val quizUseCase: QuizUseCase
) : ViewModel() {
    private val currentUserId = authenticationUseCase.getCurrentUserId()

    private val _state = MutableStateFlow(UserUIModel("", "", "0"))
    val state get() = _state.asStateFlow()

    private val _availableQuiz = MutableStateFlow<Result<List<QuizUIModel>>>(Result.Loading)
    val availableQuiz get() = _availableQuiz.asStateFlow()


    init {
        getUserData()
        getAvailableQuiz()
    }

    private fun getUserData() {
        viewModelScope.launch {
            try {
                val userGPA = userDataUseCse.getUserGPA(currentUserId)
                val user = userDataUseCse.getUser(currentUserId).toUIModel(userGPA)
                _state.tryEmit(user)
            } catch (e: Error) {
            }

        }
    }

    private fun getAvailableQuiz() {
        viewModelScope.launch {
            quizUseCase.getQuiz().map {
                it.map { quiz -> quiz.toUIModel() }
            }.toResult().collectLatest {
                _availableQuiz.tryEmit(it)

            }
        }
    }


}

