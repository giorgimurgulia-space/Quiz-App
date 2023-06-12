package com.space.quizapp.data.local.database.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.data.local.database.model.entity.UserPointEntity


@Dao
interface UserPointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPoint(point: UserPointEntity)

    @Query("select * from user_point where userId = :userId")
    suspend fun getUserPoints(userId: String): List<UserPointEntity>

    @Query("select * from user_point where userId = :userId AND subjectId = :subjectId")
    suspend fun getUserSubjectPoint(userId: String, subjectId: String): List<UserPointEntity>
}