package com.space.quizapp.presentation.home.vm

import androidx.lifecycle.viewModelScope
import com.space.quizapp.common.extensions.toResult
import com.space.quizapp.common.mapper.toUIModel
import com.space.quizapp.common.resource.Result
import com.space.quizapp.domain.usecase.auth.AuthenticationUseCase
import com.space.quizapp.domain.usecase.quiz.AvailableQuizUseCase
import com.space.quizapp.domain.usecase.user.UserDataUseCse
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
    private val userDataUseCse: UserDataUseCse,
    private val authenticationUseCase: AuthenticationUseCase,
    private val availableQuizUseCase: AvailableQuizUseCase,
) : BaseViewModel() {
    private val currentUserId = authenticationUseCase.getCurrentUserId()

    private val _state = MutableStateFlow(UserUIModel("", "", "0"))
    val state get() = _state.asStateFlow()

    private val _availableQuiz =
        MutableStateFlow<Result<List<AvailableQuizUIModel>>>(Result.Loading)
    val availableQuiz get() = _availableQuiz.asStateFlow()


    fun refreshAllData(isRefreshed:Boolean = false) {
        getUserData()
        getAvailableQuiz(isRefreshed)
    }

    fun logOut() {
        authenticationUseCase.logOut()
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
                val userGPA = userDataUseCse.getUserGPA(currentUserId)
                val user = userDataUseCse.getUser(currentUserId).toUIModel(userGPA)
                _state.tryEmit(user)
            } catch (e: Error) {
                //todo error
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

