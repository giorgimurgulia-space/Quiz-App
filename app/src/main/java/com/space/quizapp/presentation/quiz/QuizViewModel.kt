package com.space.quizapp.presentation.quiz

import androidx.lifecycle.viewModelScope
import com.space.quizapp.common.extensions.toResult
import com.space.quizapp.common.resource.Result
import com.space.quizapp.common.resource.onError
import com.space.quizapp.common.resource.onLoading
import com.space.quizapp.common.resource.onSuccess
import com.space.quizapp.domain.usecase.auth.AuthenticationUseCase
import com.space.quizapp.domain.usecase.quiz.CurrentQuizUseCase
import com.space.quizapp.presentation.base.viewModel.BaseViewModel
import com.space.quizapp.presentation.model.AnswerUIModel
import com.space.quizapp.presentation.model.PointUIModel
import com.space.quizapp.presentation.model.QuestionUIModel
import com.space.quizapp.presentation.model.QuizUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val currentQuizUseCase: CurrentQuizUseCase,
) : BaseViewModel() {

    private var questionCount = 0
    private var currentQuestionIndex = 0

    private val _quiz = MutableStateFlow(QuizUIModel("", "", 0))
    val quiz get() = _quiz.asStateFlow()

    private val _question = MutableStateFlow(QuestionUIModel("", false))
    val question get() = _question.asStateFlow()

    private val _answers = MutableStateFlow<Result<List<AnswerUIModel>>>(Result.Loading)
    val answers get() = _answers.asStateFlow()


    fun startQuiz(subjectId: String) {
        viewModelScope.launch {
            val quiz = currentQuizUseCase.startQuiz(subjectId)
            questionCount = quiz.questionsCount
            _quiz.tryEmit(quiz)
        }
        getNewQuestion()
    }

    private fun getNewQuestion() {
        val newQuestion = currentQuizUseCase.getNextQuestion()

        _question.tryEmit(
            QuestionUIModel(
                newQuestion.questionTitle,
                newQuestion.questionIndex == questionCount
            )
        )
        currentQuestionIndex = newQuestion.questionIndex

        viewModelScope.launch {
            currentQuizUseCase.getNextAnswer().map {
                it.map { answer ->
                    AnswerUIModel(answer.answerId, answer.answerTitle, answer.answerStatus)
                }
            }.toResult().collectLatest {
                _answers.tryEmit(it)
            }
        }
    }

    fun onAnswerClick(answerIndex: Int) {
        viewModelScope.launch {
            currentQuizUseCase.setUserAnswer(answerIndex).map {
                it.map { answer ->
                    AnswerUIModel(answer.answerId, answer.answerTitle, answer.answerStatus)
                }
            }.toResult().collect {
                _answers.tryEmit(it)
            }
        }
    }
}