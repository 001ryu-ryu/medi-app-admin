package com.iftikar.mediadmin.presentation.screens.users_screen

import com.iftikar.mediadmin.domain.model.User

data class AllUsersState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)