package com.space.quizapp.di

import android.content.Context
import androidx.room.Room
import com.space.quizapp.data.local.QuizDatabase
import com.space.quizapp.data.local.database.model.dao.UserDao
import com.space.quizapp.data.local.database.model.dao.UserPointDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class QuizDatabaseModule {
    @Provides
    @Singleton
    fun provideChatDatabase(@ApplicationContext context: Context): QuizDatabase =
        Room.databaseBuilder(
            context,
            QuizDatabase::class.java,
            "quiz.db"
        ).build()

    @Provides
    @Singleton
    fun provideUserDao(db: QuizDatabase): UserDao = db.getUserDao()

    @Provides
    @Singleton
    fun provideUserPointDao(db: QuizDatabase): UserPointDao = db.getUserPointDao()
}