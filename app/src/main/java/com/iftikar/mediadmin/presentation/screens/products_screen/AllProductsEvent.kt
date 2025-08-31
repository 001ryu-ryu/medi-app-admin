package com.iftikar.mediadmin.presentation.screens.products_screen

sealed interface AllProductsEvent {
    data object Idle : AllProductsEvent
    data class GoToSpecificProduct(val productId: String) : AllProductsEvent
    data object GoToAddProductScreen : AllProductsEvent
    data object Refresh : AllProductsEvent
    data class ToggleCategorizedSwitch(val checked: Boolean) : AllProductsEvent
}