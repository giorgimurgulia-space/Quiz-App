package com.space.quizapp.data.repository

import com.space.quizapp.common.mapper.toDomainModel
import com.space.quizapp.data.remote.api.ApiService
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

    override suspend fun getAvailableQuizList(refresh: Boolean): Flow<List<AvailableQuizModel>> =
        flow {
            if (availableQuiz.get().isNullOrEmpty() || refresh) {
                makeApiCall()
                emit(availableQuiz.get())
            } else
                emit(availableQuiz.get())
        }

    private suspend fun makeApiCall() {
        val response = apiService.getAvailableQuiz()
        if (response.isSuccessful)
            availableQuiz.set(response.body()!!.map {
                it.toDomainModel()
            })
    }

}