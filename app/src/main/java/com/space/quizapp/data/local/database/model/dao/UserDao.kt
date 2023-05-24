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

    @Query("select * from user where userName = :userName")
    suspend fun getUser(userName: String): UserEntity

    @Query("SELECT EXISTS(SELECT * FROM user WHERE userName = :userName)")
    suspend fun isUserNameExist(userName: String): Boolean
}