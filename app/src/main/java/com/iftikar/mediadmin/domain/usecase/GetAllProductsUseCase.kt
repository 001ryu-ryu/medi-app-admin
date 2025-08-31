package com.iftikar.mediadmin.domain.usecase

import com.iftikar.mediadmin.domain.repository.ProductRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(private val productRepository: ProductRepository) {
    operator fun invoke() = productRepository.getAllProducts()
}