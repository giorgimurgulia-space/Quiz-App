package com.space.quizapp.data.repository

import com.space.quizapp.data.remote.api.ApiService
import com.space.quizapp.data.remote.dto.QuizDto
import com.space.quizapp.domain.repository.QuizRepository
import retrofit2.Response
import javax.inject.Inject

class QuizRepositoryImpl@Inject constructor(
    private val apiService: ApiService
    ):QuizRepository {
    override suspend fun getQuiz(): Response<List<QuizDto>> {
        return apiService.getQuiz()
    }
}