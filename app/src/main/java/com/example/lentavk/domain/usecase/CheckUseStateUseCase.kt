package com.example.testimcompose.domain.usecase

import com.example.lentavk.domain.repository.PostRepository
import javax.inject.Inject

class CheckUseStateUseCase @Inject constructor(private val repository: PostRepository) {

    suspend operator fun invoke() {
        return repository.checkUserState()
    }
}