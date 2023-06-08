package com.space.quizapp.data.repository

import com.space.quizapp.data.local.database.model.dao.UserDao
import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.domain.repository.AuthenticationRepository
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val dao: UserDao) :
    AuthenticationRepository {

    private var currentUserId: AtomicReference<String> = AtomicReference(null)

    override suspend fun checkUser(username: String): Boolean {
        return dao.checkUsername(username)
    }

    override suspend fun signUpUser(username: String): Boolean {
        dao.insertUser(UserEntity(getNewId(), username))

        return checkUser(username)
    }

    override suspend fun signInUser(username: String): Boolean {
        val userId = dao.getUserByUsername(username).userId
        currentUserId.set(userId)

        return userId == currentUserId.get()
    }

    override fun logOutUser() {
        currentUserId.set(null)
    }

    override fun getCurrentUserId(): String {
        return currentUserId.get()
    }

    private fun getNewId(): String {
        return UUID.randomUUID().toString()
    }

}
