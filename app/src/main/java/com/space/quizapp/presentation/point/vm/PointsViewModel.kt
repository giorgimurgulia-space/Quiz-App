package com.space.quizapp.presentation.point.vm

import androidx.lifecycle.viewModelScope
import com.space.quizapp.common.extensions.toResult
import com.space.quizapp.common.mapper.toUIModel
import com.space.quizapp.common.resource.Result
import com.space.quizapp.domain.usecase.auth.AuthenticationUseCase
import com.space.quizapp.domain.usecase.auth.GetCurrentUseIdUseCase
import com.space.quizapp.domain.usecase.auth.LogOutUseCase
import com.space.quizapp.domain.usecase.user.UserDataUseCse
import com.space.quizapp.presentation.base.vm.BaseViewModel
import com.space.quizapp.presentation.home.ui.HomeFragmentDirections
import com.space.quizapp.presentation.model.PointUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointsViewModel @Inject constructor(
    private val getCurrentUseIdUseCase: GetCurrentUseIdUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val userDataUseCse: UserDataUseCse,
) : BaseViewModel() {

    private val currentUserId = getCurrentUseIdUseCase.invoke()

    private val _points = MutableStateFlow<Result<List<PointUIModel>>>(Result.Loading)
    val points get() = _points.asStateFlow()

    init {
        getPoints()
    }

    fun refreshAllData() {
        getPoints()
    }

    fun logOut() {
        logOutUseCase.invoke()
        navigate(HomeFragmentDirections.actionGlobalLogOut())
    }

    private fun getPoints() {
        viewModelScope.launch {
            userDataUseCse.getUserPoints(currentUserId).map {
                it.map { point -> point.toUIModel() }
            }.toResult().collectLatest {
                _points.tryEmit(it)
            }
        }
    }

}