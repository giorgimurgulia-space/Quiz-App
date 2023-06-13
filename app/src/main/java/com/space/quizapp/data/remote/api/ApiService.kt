package com.space.quizapp.data.remote.api

import com.space.quizapp.data.remote.dto.QuizDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("9a2cbd2f-d77c-498a-a410-91351fe42577")
    suspend fun getQuiz(): Response<List<QuizDto>>
}