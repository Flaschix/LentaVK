package com.example.lentavk.domain.entity

sealed class UserState {

    data object Authorized: UserState()

    data object NotAuthorized: UserState()

    data object Init: UserState()
}