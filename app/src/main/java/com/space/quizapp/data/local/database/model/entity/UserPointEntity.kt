package com.space.quizapp.data.local.database.model.entity

import androidx.room.Entity


@Entity(tableName = "user_point",primaryKeys = ["userId", "subjectId"])
data class UserPointEntity(
    val userId: String,
    val subjectId: String,
    var point: Float
)