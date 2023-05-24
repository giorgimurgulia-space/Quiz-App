package com.space.quizapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.space.quizapp.data.local.database.model.dao.UserDao
import com.space.quizapp.data.local.database.model.entity.UserEntity


@Database(
    entities = [UserEntity::class], version = 1
)

abstract class QuizDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}