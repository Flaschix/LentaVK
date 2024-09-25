package com.example.testimcompose.domain.usecase

import com.example.lentavk.domain.entity.UserState
import com.example.lentavk.domain.repository.PostRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetUserStateFlowUseCase @Inject constructor(private val repository: PostRepository) {

    operator fun invoke(): StateFlow<UserState> {
        return repository.getUserStateFlow()
    }
}