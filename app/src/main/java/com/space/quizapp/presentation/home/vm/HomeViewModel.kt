package com.space.quizapp.presentation.home.vm

import androidx.lifecycle.viewModelScope
import com.space.quizapp.common.extensions.toResult
import com.space.quizapp.common.mapper.toUIModel
import com.space.quizapp.common.resource.Result
import com.space.quizapp.domain.usecase.auth.GetCurrentUserIdUseCase
import com.space.quizapp.domain.usecase.auth.LogOutUseCase
import com.space.quizapp.domain.usecase.quiz.AvailableQuizUseCase
import com.space.quizapp.domain.usecase.user.GetUserDataUseCse
import com.space.quizapp.domain.usecase.user.GetUserGpaUseCse
import com.space.quizapp.presentation.base.vm.BaseViewModel
import com.space.quizapp.presentation.home.ui.HomeFragmentDirections
import com.space.quizapp.presentation.model.AvailableQuizUIModel
import com.space.quizapp.presentation.model.UserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataUseCse: GetUserDataUseCse,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserGpaUseCse: GetUserGpaUseCse,
    private val logOutUseCase: LogOutUseCase,
    private val availableQuizUseCase: AvailableQuizUseCase,
) : BaseViewModel() {
    private val currentUserId = getCurrentUserIdUseCase.invoke()

    private val _state = MutableStateFlow(UserUIModel())
    val state get() = _state.asStateFlow()

    private val _availableQuiz =
        MutableStateFlow<Result<List<AvailableQuizUIModel>>>(Result.Loading)
    val availableQuiz get() = _availableQuiz.asStateFlow()


    fun refreshAllData(isRefreshed: Boolean = false) {
        getUserData()
        getAvailableQuiz(isRefreshed)
    }

    fun logOut() {
        logOutUseCase.invoke()
        navigate(HomeFragmentDirections.actionGlobalLogOut())
    }

    fun navigateToPointsPage() {
        navigate(HomeFragmentDirections.actionGlobalPointsFragment())
    }

    fun onQuizClick(subjectId: String) {
        navigate(HomeFragmentDirections.actionGlobalQuizFragment(subjectId))
    }

    private fun getUserData() {
        viewModelScope.launch {
            try {
                val userGPA = getUserGpaUseCse.invoke(currentUserId)
                val user = userDataUseCse.invoke(currentUserId).toUIModel(userGPA)
                _state.tryEmit(user)
            } catch (e: Error) {
                //todo base
            }
        }
    }

    private fun getAvailableQuiz(isRefreshed: Boolean = false) {
        viewModelScope.launch {
            availableQuizUseCase.getAvailableQuiz(isRefreshed).map {
                it.map { quiz -> quiz.toUIModel() }
            }.toResult().collectLatest {
                _availableQuiz.tryEmit(it)
            }
        }
    }
}

