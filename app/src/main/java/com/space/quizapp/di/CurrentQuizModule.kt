package com.space.quizapp.di

import com.space.quizapp.data.repository.AvailableQuizRepositoryImpl
import com.space.quizapp.data.repository.CurrentQuizRepositoryImpl
import com.space.quizapp.domain.repository.AvailableQuizRepository
import com.space.quizapp.domain.repository.CurrentQuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CurrentQuizModule {

    @Binds
    @Singleton
    abstract fun bindCurrentQuizRepository(CurrentQuizRepositoryImpl: CurrentQuizRepositoryImpl): CurrentQuizRepository
}