package com.iftikar.mediadmin.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    object HomeScreen : Routes

    @Serializable
    object UsersScreen : Routes

    @Serializable
    object AllProductsScreen : Routes

    @Serializable
    data class SpecificProductScreen(val productId: String) : Routes

    @Serializable
    data object AddProductScreen : Routes
}