package com.space.quizapp.data.remote.api

import com.space.quizapp.data.remote.dto.QuizDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("13ed39b3-f36d-462f-a403-8ea79ce4f2b9")
    suspend fun getQuiz(): Response<List<QuizDto>>
}