package com.space.quizapp.data.repository

import com.space.quizapp.common.ApiError
import com.space.quizapp.common.mapper.toAvailableQuizModel
import com.space.quizapp.data.remote.api.ApiService
import com.space.quizapp.data.remote.dto.QuizDto
import com.space.quizapp.domain.model.AvailableQuizModel
import com.space.quizapp.domain.repository.AvailableQuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class AvailableQuizRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AvailableQuizRepository {

    private var availableQuiz: AtomicReference<List<AvailableQuizModel>> =
        AtomicReference(emptyList())


    //change flow to list
    override suspend fun getAvailableQuizList(isRefreshed: Boolean): Flow<List<AvailableQuizModel>> =
        flow {
//        val response = apiService.getQuiz()
//        if (response.isSuccessful) {
//            val quiz = response.body()!!.map {
//                it.toAvailableQuizModel()
//            }
//            emit(quiz)
//        } else {
//            //todo
//            throw ApiError(null)
//        }

            if (availableQuiz.get().isNullOrEmpty() || isRefreshed) {
                makeApiCall()
                emit(availableQuiz.get())
            } else
                emit(availableQuiz.get())

        }

    private suspend fun makeApiCall() {
        val response = apiService.getQuiz()
        if (response.isSuccessful)
            availableQuiz.set(response.body()!!.map {
                it.toAvailableQuizModel()
            })
    }

}