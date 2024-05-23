package com.sd.products.domain.model

import com.sd.products.domain.dto.Product

data class ModelCurrentProduct(
    val loading: Boolean = false,
    val product: Product = Product(),
    val error: Boolean = false
)
