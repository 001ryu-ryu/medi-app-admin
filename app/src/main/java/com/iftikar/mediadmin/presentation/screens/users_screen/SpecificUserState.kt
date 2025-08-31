package com.iftikar.mediadmin.presentation.screens.users_screen

import com.iftikar.mediadmin.domain.model.User

data class SpecificUserState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val blockMessage: String = ""
)
