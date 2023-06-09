package com.space.quizapp.domain.repository

import com.space.quizapp.domain.model.QuizModel
import kotlinx.coroutines.flow.Flow

interface AvailableQuizRepository {
    suspend fun getAvailableQuizList(): Flow<List<QuizModel>>
}