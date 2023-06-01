package com.space.quizapp.common.mapper

import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.data.local.database.model.entity.UserPointEntity
import com.space.quizapp.data.remote.dto.QuizDto
import com.space.quizapp.domain.model.QuizModel
import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.model.PointModel
import com.space.quizapp.ui.model.QuizUIModel
import com.space.quizapp.ui.model.UserUIModel

fun UserEntity.toDomainModel() = UserModel(userId, username)

fun UserPointEntity.toDomainModel() = PointModel(userId, subjectId, point)

fun UserModel.toUIModel(userGPA: String) = UserUIModel(userId, username, userGPA)

fun QuizDto.toDomainModel() = QuizModel(id, quizTitle, quizDescription)

fun QuizModel.toUIModel() = QuizUIModel(id, quizTitle, quizDescription)

