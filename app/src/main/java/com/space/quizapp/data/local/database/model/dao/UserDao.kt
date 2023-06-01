package com.space.quizapp.data.local.database.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.space.quizapp.data.local.database.model.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("select * from user where userId = :userId")
    suspend fun getUserByIdUser(userId: String): UserEntity

    @Query("select * from user where username = :username")
    suspend fun getUserByUsername(username: String): UserEntity

    @Query("select exists(select * from user where userName = :username)")
    suspend fun isUsernameExist(username: String): Boolean
}