package com.iftikar.mediadmin.domain.repository

import com.iftikar.mediadmin.data.remote.ApiOperation
import com.iftikar.mediadmin.data.remote.model.InsertProductResponseDto
import com.iftikar.mediadmin.data.remote.model.ProductRequestDto
import com.iftikar.mediadmin.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(): Flow<ApiOperation<List<Product>>>
    fun getSpecificProduct(productId: String): Flow<ApiOperation<Product>>
    fun insertProduct(product: ProductRequestDto): Flow<ApiOperation<InsertProductResponseDto>>
}