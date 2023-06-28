package com.space.quizapp.data.remote.api

import com.space.quizapp.data.remote.dto.AvailableQuizDto
import com.space.quizapp.data.remote.dto.QuizDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("8d11a463-b9e3-48ae-9959-2c879f839d68")
    suspend fun getAvailableQuiz(): Response<List<AvailableQuizDto>>

    @GET("13411834-059d-44e7-8d3e-e730daa6bb3f")
    suspend fun getQuiz(): Response<List<QuizDto>>
}