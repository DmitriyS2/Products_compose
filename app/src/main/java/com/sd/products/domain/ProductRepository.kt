package com.sd.products.domain

import com.sd.products.domain.dto.Product

interface ProductRepository {
    suspend fun getCurrentProduct(id: Int): Product
    suspend fun getListProducts(): List<Product>
}