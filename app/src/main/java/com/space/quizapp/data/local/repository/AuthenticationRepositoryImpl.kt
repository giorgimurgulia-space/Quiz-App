package com.space.quizapp.data.local.repository

import com.space.quizapp.common.mapper.toDomainModel
import com.space.quizapp.data.local.database.model.dao.UserDao
import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.repository.AuthenticationRepository
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val dao: UserDao) :
    AuthenticationRepository {

    private var currentUserId: AtomicReference<String> = AtomicReference(null)

    override suspend fun checkUser(username: String): Boolean {
        return dao.isUsernameExist(username)
    }

    override suspend fun signUpUser(username: String): Boolean {
        dao.insertUser(UserEntity(getNewId(), username))

        return checkUser(username)
    }

    override suspend fun signInUser(username: String): Boolean {
        currentUserId.set(dao.getByUsername(username).userId)

        return dao.getByUsername(username).userId == currentUserId.get()
    }

    override suspend fun getCurrentUser(): UserModel {
        return dao.getByIdUser(currentUserId.get()).toDomainModel()
    }


    private fun getNewId(): String {
        return UUID.randomUUID().toString()
    }

}
