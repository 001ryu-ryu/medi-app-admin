package com.iftikar.mediadmin.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    object HomeScreen : Routes
    @Serializable
    object UsersScreen : Routes
}