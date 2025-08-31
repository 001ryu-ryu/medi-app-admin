package com.iftikar.mediadmin.presentation.screens.addproduct_screen

sealed interface AddProductScreenEvent {
    data class OnNameChange(val name: String) : AddProductScreenEvent
    data class OnCategoryChange(val category: String) : AddProductScreenEvent
    data class OnPriceChange(val price: String) : AddProductScreenEvent
    data class OnStockChange(val stock: String) : AddProductScreenEvent
    data object InsertProduct : AddProductScreenEvent
}