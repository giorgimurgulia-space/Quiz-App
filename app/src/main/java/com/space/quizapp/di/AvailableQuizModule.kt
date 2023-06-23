package com.space.quizapp.di

import com.space.quizapp.data.repository.AvailableQuizRepositoryImpl
import com.space.quizapp.domain.repository.AvailableQuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AvailableQuizModule {

    @Binds
    @Singleton
    abstract fun bindQuizRepository(quizRepositoryImpl: AvailableQuizRepositoryImpl): AvailableQuizRepository
}