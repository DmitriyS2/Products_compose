package com.sd.products.domain.model

import com.sd.products.domain.dto.Product

data class ModelProducts(
    val loading: Boolean = false,
    val products: List<Product> = listOf(),
    val error: Boolean = false
)
