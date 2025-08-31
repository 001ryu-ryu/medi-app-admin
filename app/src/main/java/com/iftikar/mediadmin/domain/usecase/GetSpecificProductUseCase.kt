package com.iftikar.mediadmin.domain.usecase

import com.iftikar.mediadmin.domain.repository.ProductRepository
import javax.inject.Inject

class GetSpecificProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productId: String) = productRepository.getSpecificProduct(productId)
}