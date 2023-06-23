package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.AvailableQuizModel
import kotlinx.coroutines.flow.Flow

interface AvailableQuizRepository {
    suspend fun getAvailableQuizList(isRefreshed: Boolean): Flow<List<AvailableQuizModel>>
}