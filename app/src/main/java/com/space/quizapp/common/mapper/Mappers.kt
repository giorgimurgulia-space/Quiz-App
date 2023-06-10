package com.space.quizapp.common.mapper

import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.data.local.database.model.entity.UserPointEntity
import com.space.quizapp.data.remote.dto.QuizDto
import com.space.quizapp.domain.model.AvailableQuizModel
import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.model.PointModel
import com.space.quizapp.presentation.model.PointUIModel
import com.space.quizapp.presentation.model.AvailableQuizUIModel
import com.space.quizapp.presentation.model.UserUIModel

fun UserEntity.toDomainModel() = UserModel(userId, username)

fun UserPointEntity.toDomainModel() =
    PointModel(userId, subjectId, quizTitle, quizDescription, quizIcon, point)

fun PointModel.toUIModel() =
    PointUIModel(userId, subjectId, quizTitle, quizDescription, quizIcon, point)

fun UserModel.toUIModel(userGPA: String) = UserUIModel(userId, username, userGPA)

fun QuizDto.toDomainModel() = AvailableQuizModel(id, quizTitle, quizDescription, quizIcon)

fun AvailableQuizModel.toUIModel() = AvailableQuizUIModel(id, quizTitle, quizDescription, quizIcon)



