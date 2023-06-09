package com.space.quizapp.presentation.home

import androidx.lifecycle.viewModelScope
import com.space.quizapp.common.extensions.toResult
import com.space.quizapp.common.mapper.toUIModel
import com.space.quizapp.common.resource.Result
import com.space.quizapp.data.local.database.model.dao.UserPointDao
import com.space.quizapp.data.local.database.model.entity.UserPointEntity
import com.space.quizapp.domain.usecase.auth.AuthenticationUseCase
import com.space.quizapp.domain.usecase.quiz.QuizUseCase
import com.space.quizapp.domain.usecase.user.UserDataUseCse
import com.space.quizapp.presentation.base.viewModel.BaseViewModel
import com.space.quizapp.presentation.model.QuizUIModel
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
    private val quizUseCase: QuizUseCase,
    //For GPA Test
    private val point: UserPointDao
) : BaseViewModel() {
    //For GPA Test
    private val currentUserId = authenticationUseCase.getCurrentUserId()

    private val _state = MutableStateFlow(UserUIModel("", "", "0"))
    val state get() = _state.asStateFlow()

    private val _availableQuiz = MutableStateFlow<Result<List<QuizUIModel>>>(Result.Loading)
    val availableQuiz get() = _availableQuiz.asStateFlow()


    init {
        //For GPA Test
        setPoint()

        getUserData()
        getAvailableQuiz()
    }

    fun refreshAllData() {
        getUserData()
        getAvailableQuiz()
    }

    fun logOut() {
        authenticationUseCase.logOut()
        navigate(HomeFragmentDirections.actionGlobalLogOut())
    }

    fun onGPADetailClick() {
        navigate(HomeFragmentDirections.actionGlobalPointsFragment())
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

    private fun getAvailableQuiz() {
        viewModelScope.launch {
            quizUseCase.getQuiz().map {
                it.map { quiz -> quiz.toUIModel() }
            }.toResult().collectLatest {
                _availableQuiz.tryEmit(it)
            }
        }
    }

    //For GPA Test
    private fun setPoint() {
        viewModelScope.launch {

            point.insertUserPoint(
                (UserPointEntity(
                    authenticationUseCase.getCurrentUserId(),
                    "1",
                    "გეოგრაფია",
                    "კითხვები გეოგრაფიიდან",
                    "https://scontent.xx.fbcdn.net/v/t1.15752-9/343573230_204266569212395_1404329334796031022_n.png?_nc_cat=101&ccb=1-7&_nc_sid=aee45a&_nc_ohc=6D3IcI5T_84AX_iC0LA&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdTC0KrDNMhIzUpGI-7fYnKg9ZtvT58AdO9rvFeskZwzGQ&oe=649DA746",
                    1.toFloat()
                ))
            )
            point.insertUserPoint(
                (UserPointEntity(
                    authenticationUseCase.getCurrentUserId(),
                    "2",
                    "გეოგრაფია",
                    "კითხვები გეოგრაფიიდან",
                    "https://scontent.xx.fbcdn.net/v/t1.15752-9/343573230_204266569212395_1404329334796031022_n.png?_nc_cat=101&ccb=1-7&_nc_sid=aee45a&_nc_ohc=6D3IcI5T_84AX_iC0LA&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdTC0KrDNMhIzUpGI-7fYnKg9ZtvT58AdO9rvFeskZwzGQ&oe=649DA746",
                    1.1.toFloat()
                ))
            )
            point.insertUserPoint(
                (UserPointEntity(
                    authenticationUseCase.getCurrentUserId(),
                    "3",
                    "გეოგრაფია",
                    "კითხვები გეოგრაფიიდან",
                    "https://scontent.xx.fbcdn.net/v/t1.15752-9/343573230_204266569212395_1404329334796031022_n.png?_nc_cat=101&ccb=1-7&_nc_sid=aee45a&_nc_ohc=6D3IcI5T_84AX_iC0LA&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdTC0KrDNMhIzUpGI-7fYnKg9ZtvT58AdO9rvFeskZwzGQ&oe=649DA746",
                    1.5.toFloat()
                ))
            )
        }
    }
}

