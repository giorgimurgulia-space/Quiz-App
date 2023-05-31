package com.space.quizapp.common.mapper

import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.data.local.database.model.entity.UserPointEntity
import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.model.UserPoint
import com.space.quizapp.ui.model.UserUIModel

fun UserEntity.toDomainModel() = UserModel(userId, username)
fun UserPointEntity.toDomainModel() = UserPoint(userId, subjectId, point)

fun UserModel.toUIModel(gpa: String, point: List<UserPoint>) =
    UserUIModel(userId, username, gpa, point)