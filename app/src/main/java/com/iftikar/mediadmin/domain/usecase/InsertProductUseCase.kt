package com.iftikar.mediadmin.domain.usecase

import com.iftikar.mediadmin.data.remote.model.ProductRequestDto
import com.iftikar.mediadmin.domain.repository.ProductRepository
import javax.inject.Inject

class InsertProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productRequestDto: ProductRequestDto)
    = productRepository.insertProduct(productRequestDto)
}