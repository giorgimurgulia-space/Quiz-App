package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.AvailableQuizModel
import kotlinx.coroutines.flow.Flow

interface CurrentQuizRepository {
    suspend fun getQuizById(subjectId: String): Flow<List<AvailableQuizModel>>
}