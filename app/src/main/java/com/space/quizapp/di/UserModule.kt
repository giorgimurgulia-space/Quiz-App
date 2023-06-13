package com.space.quizapp.di

import com.space.quizapp.data.repository.AuthenticationRepositoryImpl
import com.space.quizapp.data.repository.UserDataRepositoryImpl
import com.space.quizapp.domain.repository.AuthenticationRepository
import com.space.quizapp.domain.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindUserDataRepository(userDataRepositoryImpl: UserDataRepositoryImpl): UserDataRepository

}