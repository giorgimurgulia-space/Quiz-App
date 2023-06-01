package com.space.quizapp.domain.repository

import com.space.quizapp.data.remote.dto.QuizDto
import retrofit2.Response

interface QuizRepository {
    suspend fun getQuiz(): Response<List<QuizDto>>
}