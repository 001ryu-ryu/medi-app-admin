package com.iftikar.mediadmin.presentation.screens.users_screen

import com.iftikar.mediadmin.domain.model.User

sealed interface UsersScreenState {
    object Idle : UsersScreenState
    object Loading : UsersScreenState
    data class Success(val users: List<User>?) : UsersScreenState
    data class Failure(val error: String) : UsersScreenState
}