package com.space.quizapp.data.local.repository

import com.space.quizapp.common.mapper.toDomainModel
import com.space.quizapp.data.local.database.model.dao.UserDao
import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.repository.UserRepository
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dao: UserDao) : UserRepository {

    private var currentUserId: AtomicReference<String> = AtomicReference(null)


    override suspend fun checkUser(username: String): Boolean {
        return dao.isUsernameExist(username)
    }

    override suspend fun signUpUser(username: String): UserModel {
        dao.insertUser(UserEntity(getNewId(), username))

        return dao.getByUsername(username).toDomainModel()
    }

    override fun signInUser(userId: String): Boolean {
        currentUserId.set(userId)

        return userId == currentUserId.get()
    }

    override suspend fun getUser(username: String): UserModel {
        return dao.getByUsername(username).toDomainModel()
    }

    override suspend fun getCurrentUser(): UserModel {
        return getUserById(currentUserId.get()).toDomainModel()
    }


    private suspend fun getUserById(userId: String): UserEntity {
        return dao.getByIdUser(userId)
    }

    private fun getNewId(): String {
        return UUID.randomUUID().toString()
    }


}
