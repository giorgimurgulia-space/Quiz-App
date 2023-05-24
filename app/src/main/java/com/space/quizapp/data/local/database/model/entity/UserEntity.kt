package com.space.quizapp.data.local.database.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val userName: String
)