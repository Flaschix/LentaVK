package com.example.lentavk.domain.repository

import com.example.lentavk.domain.entity.Post
import com.example.lentavk.domain.entity.PostComment
import com.example.lentavk.domain.entity.UserState
import kotlinx.coroutines.flow.StateFlow

interface PostRepository {

    fun getUserStateFlow(): StateFlow<UserState>

    fun getPosts(): StateFlow<List<Post>>

    fun getComments(post: Post): StateFlow<List<PostComment>>

    suspend fun loadNextPosts()

    suspend fun checkUserState()

    suspend fun changeLikeStatus(post: Post)
}