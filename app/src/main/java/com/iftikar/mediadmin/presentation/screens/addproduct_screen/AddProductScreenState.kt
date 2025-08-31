package com.iftikar.mediadmin.presentation.screens.addproduct_screen

data class AddProductScreenState(
    val name: String = "",
    val category: String = "",
    val price: String = "",
    val stock: String = "",
    val isLoading: Boolean = false,
    val successMessage: String = "",
    val errorMessage: String? = null
)
