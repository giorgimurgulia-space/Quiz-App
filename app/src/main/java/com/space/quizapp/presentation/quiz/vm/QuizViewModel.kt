package com.space.quizapp.presentation.quiz.vm

import androidx.lifecycle.viewModelScope
import com.space.quizapp.R
import com.space.quizapp.common.extensions.toResult
import com.space.quizapp.common.mapper.toPointString
import com.space.quizapp.common.mapper.toUIModel
import com.space.quizapp.common.resource.onError
import com.space.quizapp.common.resource.onLoading
import com.space.quizapp.common.resource.onSuccess
import com.space.quizapp.domain.model.PointModel
import com.space.quizapp.domain.usecase.auth.GetCurrentUserIdUseCase
import com.space.quizapp.domain.usecase.quiz.*
import com.space.quizapp.domain.usecase.user.InsertUserPointUseCse
import com.space.quizapp.presentation.base.vm.BaseViewModel
import com.space.quizapp.presentation.model.DialogUIModel
import com.space.quizapp.presentation.model.QuizUIModel
import com.space.quizapp.presentation.quiz.ui.QuizPagePayLoad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val startQuizUseCase: StartQuizUseCase,
    private val getNextQuestionUseCase: GetNextQuestionUseCase,
    private val getNextAnswersUseCase: GetNextAnswersUseCase,
    private val insertAnswerUseCase: InsertAnswerUseCase,
    private val getCurrentUseIdUseCase: GetCurrentUserIdUseCase,
    private val getQuizPointUseCase: GetQuizPointUseCase,
    private val insertUserPointUseCse: InsertUserPointUseCse
) : BaseViewModel() {

    private lateinit var currentQuiz: QuizUIModel

    private val _quizState = MutableStateFlow(QuizPagePayLoad())
    val quizState get() = _quizState.asStateFlow()

    fun startQuiz(subjectId: String?) {
        if (subjectId.isNullOrEmpty()) {
            setDialog(
                DialogUIModel(
                    title = R.string.error_message_close,
                    closeButton = { navigateBack() },
                )
            )
        } else {
            viewModelScope.launch {
                try {
                    val quiz = startQuizUseCase.invoke(subjectId).toUIModel()
                    currentQuiz = quiz
                    _quizState.tryEmit(_quizState.value.copy(quizTitle = quiz.quizTitle))
                    getQuestion()
                } catch (e: Exception) {
                    setDialog(
                        DialogUIModel(
                            title = R.string.error_message_close,
                            closeButton = { navigateBack() },
                        )
                    )
                }
            }
        }
    }

    fun onAnswerClick(answerIndex: Int) {
        insertAnswerUseCase.invoke(answerIndex)
    }

    fun onSubmitButtonClick() {
        if (_quizState.value.isLastQuestion)
            finishQuiz()
        else
            getQuestion()
    }

    fun onCancelClick() {
        setDialog(
            DialogUIModel(
                title = R.string.cancel_quiz,
                yesButton = { cancelQuiz() }
            )
        )
    }

    private fun cancelQuiz() {
        val point = getQuizPointUseCase.invoke()

        if (point >= 1)
            finishQuiz()
        else
            setDialog(
                DialogUIModel(
                    description = point.toPointString(),
                    closeButton = { navigateBack() }
                ))
    }

    private fun finishQuiz() {
        val point = getQuizPointUseCase.invoke()
        insertQuizPoint(point)
        setDialog(
            DialogUIModel(
                icon = true,
                title = R.string.congratulation,
                description = point.toPointString(),
                closeButton = { navigateBack() }
            ))
    }

    private fun getQuestion() {
        val newQuestion = getNextQuestionUseCase.invoke()

        _quizState.tryEmit(
            _quizState.value.copy(
                question = newQuestion.questionTitle,
                correctAnswerIndex = newQuestion.correctAnswerIndex,
                isLastQuestion = newQuestion.questionIndex == currentQuiz.questionsCount - 1
            )
        )

        viewModelScope.launch {
            getNextAnswersUseCase.invoke().map {
                it.map { answer ->
                    answer.toUIModel()
                }
            }.toResult().collectLatest {
                it.onSuccess { answers ->
                    closeLoaderDialog()
                    _quizState.tryEmit(_quizState.value.copy(answers = answers))
                }
                it.onLoading {
                    setDialog(DialogUIModel(isProgressbar = true))
                }
                it.onError {
                    setDialog(
                        DialogUIModel(
                            title = R.string.error_message_close,
                            yesButton = { navigateBack() },
                        )
                    )
                }
            }
        }
    }

    private fun insertQuizPoint(point: Float) {
        viewModelScope.launch {
            insertUserPointUseCse.invoke(
                PointModel(
                    getCurrentUseIdUseCase.invoke(),
                    currentQuiz.id,
                    currentQuiz.quizTitle,
                    currentQuiz.quizDescription,
                    currentQuiz.quizIcon,
                    point,
                    currentQuiz.questionsCount
                )
            )
        }
    }

}