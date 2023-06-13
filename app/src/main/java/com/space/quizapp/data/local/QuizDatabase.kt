package com.space.quizapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.space.quizapp.data.local.database.model.dao.UserDao
import com.space.quizapp.data.local.database.model.dao.UserPointDao
import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.data.local.database.model.entity.UserPointEntity


@Database(
    entities = [
        UserEntity::class,
        UserPointEntity::class
    ],
    version = 1
)

abstract class QuizDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getUserPointDao(): UserPointDao
}