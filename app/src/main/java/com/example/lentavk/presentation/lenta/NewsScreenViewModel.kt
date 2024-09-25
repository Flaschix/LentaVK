package com.example.lentavk.presentation.lenta

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lentavk.domain.entity.Post
import com.example.lentavk.utils.mergeWith
import com.example.testimcompose.domain.usecase.ChangeLikeStatusUseCase
import com.example.testimcompose.domain.usecase.GetPostsUseCase
import com.example.testimcompose.domain.usecase.LoadNextPostsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsScreenViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val loadNextPostsUseCase: LoadNextPostsUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
) : ViewModel() {

    //vk
    private val exceptionHandler = CoroutineExceptionHandler{ _, _ ->
        Log.d("exceptionHandler", "ExceptionHandler catch a error")
    }

    private val postsFlow = getPostsUseCase()

    private val loadNextPostsEvents = MutableSharedFlow<Unit>()

    private val loadNextDataFlow = flow {
        loadNextPostsEvents.collect{
            emit(NewsScreenState.Success(posts = postsFlow.value, nexDataIsLoading = true))
        }
    }

    val screenState = postsFlow
        .filter { it.isNotEmpty() }
        .map { NewsScreenState.Success(it) as NewsScreenState }
        .onStart { emit(NewsScreenState.Loading) }
        .mergeWith(loadNextDataFlow)


    fun loadNextPosts(){
        viewModelScope.launch {
            loadNextPostsEvents.emit(Unit)
            loadNextPostsUseCase()
        }
    }

    fun changeLikeStatus(post: Post){
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(post)
        }
    }
}