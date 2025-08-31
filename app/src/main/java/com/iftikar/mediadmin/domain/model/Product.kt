package com.iftikar.mediadmin.domain.model

import com.google.gson.annotations.SerializedName

data class Product(
    val category: String,
    val id: Int,
    val name: String,
    val price: Double,
    @SerializedName("product_id")
    val productId: String,
    val stock: Int
)