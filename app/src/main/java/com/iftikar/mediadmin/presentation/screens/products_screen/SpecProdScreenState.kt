package com.iftikar.mediadmin.presentation.screens.products_screen

import com.iftikar.mediadmin.domain.model.Product

data class SpecProdScreenState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null
)