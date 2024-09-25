package com.example.testimcompose.domain.usecase

import com.example.lentavk.domain.entity.Post
import com.example.lentavk.domain.entity.PostComment
import com.example.lentavk.domain.repository.PostRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repository: PostRepository) {

    operator fun invoke(post: Post): StateFlow<List<PostComment>> {
        return repository.getComments(post)
    }
}