package com.space.quizapp.data.repository

import com.space.quizapp.common.ApiError
import com.space.quizapp.common.mapper.toDomainModel
import com.space.quizapp.data.remote.api.ApiService
import com.space.quizapp.domain.model.QuizModel
import com.space.quizapp.domain.repository.AvailableQuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AvailableQuizRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AvailableQuizRepository {

    //change flow to list
    override suspend fun getAvailableQuizList(): Flow<List<QuizModel>> = flow {
        val response = apiService.getQuiz()
        if (response.isSuccessful) {
            val quiz = response.body()!!.map {
                it.toDomainModel()
            }
            emit(quiz)
        } else {
            //todo
            throw ApiError(null)
        }
    }

}