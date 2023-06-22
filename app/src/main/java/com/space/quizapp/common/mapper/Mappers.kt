package com.space.quizapp.common.mapper

import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.data.local.database.model.entity.UserPointEntity
import com.space.quizapp.data.remote.dto.QuestionDto
import com.space.quizapp.data.remote.dto.QuizDto
import com.space.quizapp.domain.model.*
import com.space.quizapp.presentation.model.*
import java.util.*

fun UserEntity.toDomainModel() = UserModel(userId, username)

fun UserPointEntity.toDomainModel() =
    PointModel(userId, subjectId, quizTitle, quizDescription, quizIcon, point)

fun PointModel.toUIModel() =
    PointUIModel(userId, subjectId, quizTitle, quizDescription, quizIcon, point)

fun UserModel.toUIModel(userGPA: String) = UserUIModel(userId, username, userGPA)

fun QuizDto.toDomainModel() = AvailableQuizModel(id, quizTitle, quizDescription, quizIcon)

fun AvailableQuizModel.toUIModel() = AvailableQuizUIModel(id, quizTitle, quizDescription, quizIcon)

fun QuizDto.toQuizDomainModel() =
    QuizModel(
        id,
        quizTitle,
        quizDescription,
        quizIcon,
        questionsCount
    )

fun QuestionDto.toDomainModel() =
    QuestionModel(
        questionTitle,
        answers.map { it.toAnswer() },
        answers.indexOf(correctAnswer),
        questionIndex
    )

fun QuizModel.toUIModel() = QuizUIModel(id, quizTitle, quizDescription, quizIcon, questionsCount)

fun QuestionModel.toUIModel(isLastQuestion: Boolean) =
    QuestionUIModel(questionTitle, answers.map { it.toUIModel() }, isLastQuestion)

fun AnswerModel.toUIModel() = AnswerUIModel(answerId, answerTitle)

fun String.toAnswer() = AnswerModel(UUID.randomUUID().toString(), this)

fun PointModel.toEntity( ) =
    UserPointEntity(userId, subjectId, quizTitle, quizDescription, quizIcon, point)



