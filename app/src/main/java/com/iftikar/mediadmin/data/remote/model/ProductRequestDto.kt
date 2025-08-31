package com.iftikar.mediadmin.data.remote.model

import com.google.gson.annotations.SerializedName

data class ProductRequestDto(
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int
)
