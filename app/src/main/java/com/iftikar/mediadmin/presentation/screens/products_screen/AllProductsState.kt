package com.iftikar.mediadmin.presentation.screens.products_screen

import com.iftikar.mediadmin.domain.model.Product

data class AllProductsState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null,
    val isCategorized: Boolean = false
)

