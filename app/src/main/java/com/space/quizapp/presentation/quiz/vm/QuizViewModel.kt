package com.space.quizapp.presentation.quiz.vm

import androidx.lifecycle.viewModelScope
import com.space.quizapp.common.extensions.toResult
import com.space.quizapp.common.mapper.toUIModel
import com.space.quizapp.common.resource.Result
import com.space.quizapp.domain.usecase.auth.AuthenticationUseCase
import com.space.quizapp.domain.usecase.quiz.CurrentQuizUseCase
import com.space.quizapp.domain.usecase.user.UserDataUseCse
import com.space.quizapp.presentation.base.vm.BaseViewModel
import com.space.quizapp.presentation.model.QuizUIModel
import com.space.quizapp.presentation.quiz.ui.QuizPagePayLoad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val userDataUseCse: UserDataUseCse,
    private val currentQuizUseCase: CurrentQuizUseCase,
) : BaseViewModel() {

    private lateinit var currentQuiz: QuizUIModel

    private val _quizState = MutableStateFlow(QuizPagePayLoad("", "", Result.Loading, false, null))
    val quizState get() = _quizState.asStateFlow()

    fun startQuiz(subjectId: String) {
        viewModelScope.launch {
            val quiz = currentQuizUseCase.startQuiz(subjectId).toUIModel()
            currentQuiz = quiz
            _quizState.tryEmit(_quizState.value.copy(quizTitle = quiz.quizTitle))
            getNewQuestion()
        }
    }

    fun onAnswerClick(answerIndex: Int) {
        viewModelScope.launch {
            currentQuizUseCase.setUserAnswer(answerIndex).map {
                it.map { answer ->
                    answer.toUIModel()
                }
            }.toResult().collect {
                _quizState.tryEmit(_quizState.value.copy(answers = it))
            }
        }
    }

    fun onSubmitButtonClick() {
        if (_quizState.value.isLastQuestion)
            finishQuiz()
        else
            getNewQuestion()
    }

    fun cancelQuiz(): Float {
        return finishQuiz()
    }

    private fun finishQuiz(): Float {
        val point = currentQuizUseCase.finishQuiz()
        insertQuizPoint(point)
        _quizState.tryEmit(_quizState.value.copy(point = point))

        return point
    }

    private fun getNewQuestion() {
        val newQuestion = currentQuizUseCase.getNextQuestion()

        _quizState.tryEmit(
            _quizState.value.copy(
                question = newQuestion.questionTitle,
                isLastQuestion = newQuestion.questionIndex == currentQuiz.questionsCount - 1
            )
        )

        viewModelScope.launch {
            currentQuizUseCase.getNextAnswer().map {
                it.map { answer ->
                    answer.toUIModel()
                }
            }.toResult().collectLatest {
                _quizState.tryEmit(_quizState.value.copy(answers = it))
            }
        }
    }

    private fun insertQuizPoint(point: Float) {
        viewModelScope.launch {
            userDataUseCse.setUserPoint(
                authenticationUseCase.getCurrentUserId(),
                currentQuiz.id,
                currentQuiz.quizTitle,
                currentQuiz.quizDescription,
                currentQuiz.quizIcon,
                point
            )
        }
    }

}