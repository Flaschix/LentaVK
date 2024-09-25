package com.example.lentavk.presentation.lenta

import com.example.lentavk.domain.entity.Post

sealed class NewsScreenState {

    object Init: NewsScreenState()

    object Loading: NewsScreenState()

    data class Success(
        val posts: List<Post>,
        val nexDataIsLoading: Boolean = false
    ): NewsScreenState()

}