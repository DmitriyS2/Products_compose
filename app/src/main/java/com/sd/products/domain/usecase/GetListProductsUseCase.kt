package com.sd.products.domain.usecase

import com.sd.products.domain.ProductRepository
import com.sd.products.domain.dto.Product
import javax.inject.Inject

class GetListProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun getListProducts(): List<Product> {
        return repository.getListProducts()
    }
}