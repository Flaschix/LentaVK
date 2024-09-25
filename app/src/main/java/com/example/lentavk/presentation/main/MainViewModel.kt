package com.example.lentavk.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lentavk.domain.entity.UserState
import com.example.testimcompose.domain.usecase.CheckUseStateUseCase
import com.example.testimcompose.domain.usecase.GetUserStateFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateFlowUseCase: GetUserStateFlowUseCase,
    private val checkAuthStateUseCase: CheckUseStateUseCase
) : ViewModel() {

    val authState: Flow<UserState> = getAuthStateFlowUseCase()


    fun performAuthResult(){
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}