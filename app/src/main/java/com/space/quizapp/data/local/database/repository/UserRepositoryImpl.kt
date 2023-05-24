package com.space.quizapp.data.local.database.repository

import com.space.quizapp.data.local.database.model.dao.UserDao
import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.domain.model.UserModel
import com.space.quizapp.domain.repository.UserRepository
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dao: UserDao) : UserRepository {

    override suspend fun startUser(userName: String): UserModel {

        return if (dao.isUserNameExist(userName)) {
            val user = dao.getUser(userName)
            UserModel(user.userId, user.userName)

        } else {
            registerNewUser(userName)

            val user = dao.getUser(userName)
            UserModel(user.userId, user.userName)
        }
    }

    private fun getNewId(): String {
        return UUID.randomUUID().toString()
    }

    private suspend fun registerNewUser(userName: String) {
        dao.insertUser(UserEntity(getNewId(), userName))
    }
}
