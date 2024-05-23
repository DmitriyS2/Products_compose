package com.sd.products.domain.usecase

import com.sd.products.domain.ProductRepository
import com.sd.products.domain.dto.Product
import javax.inject.Inject

class GetCurrentProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun getCurrentProduct(id: Int): Product {
        return repository.getCurrentProduct(id)
    }
}