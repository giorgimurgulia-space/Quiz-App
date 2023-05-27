package com.space.quizapp.common.mapper

import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.domain.model.UserModel

fun UserEntity.toDomainModel() = UserModel(userId, username)