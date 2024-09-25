package com.example.testimcompose.domain.usecase

import com.example.lentavk.domain.entity.Post
import com.example.lentavk.domain.repository.PostRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: PostRepository) {

    operator fun invoke(): StateFlow<List<Post>>{
        return repository.getPosts()
    }
}