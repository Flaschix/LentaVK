package com.example.testimcompose.domain.usecase

import com.example.lentavk.domain.entity.Post
import com.example.lentavk.domain.repository.PostRepository
import javax.inject.Inject

class ChangeLikeStatusUseCase @Inject constructor(private val repository: PostRepository) {

    suspend operator fun invoke(post: Post) {
        return repository.changeLikeStatus(post)
    }
}