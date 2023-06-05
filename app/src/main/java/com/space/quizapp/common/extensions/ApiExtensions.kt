package com.space.quizapp.common.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.space.quizapp.common.resource.Result
import com.space.quizapp.ui.home.QuizAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


fun <T : Any> Flow<T>.toResult(): Flow<Result<T>> =
    this.map { result ->
        Result.Success(result)
    }.catch<Result<T>> { e ->
        emit(Result.Error(e))
    }.onStart { emit(Result.Loading) }

fun <T : Any> Fragment.collectFlow(
    flow: Flow<T>,
    coroutineContext: CoroutineContext = Dispatchers.Unconfined,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    block: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch(coroutineContext) {
        flow.flowWithLifecycle(lifecycle, lifecycleState).collect {
            block(it)
        }
    }
}