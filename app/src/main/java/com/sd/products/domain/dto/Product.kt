package com.sd.products.domain.dto

data class Product(
    val brand: String = "",
    val category: String = "",
    val description: String = "",
    val discountPercentage: Double = NULL_DOUBLE,
    val id: Int = NULL_INT,
    val images: List<String> = emptyList(),
    val price: Int = NULL_INT,
    val rating: Double = NULL_DOUBLE,
    val stock: Int = NULL_INT,
    val thumbnail: String = "",
    val title: String = ""
) {
    companion object {
        const val NULL_INT = 0
        const val NULL_DOUBLE = 0.0
    }
}