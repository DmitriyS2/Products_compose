package com.sd.products.domain.dto

data class Review(
    val rating: Int,
    val comment: String,
    val reviewerName: String
)
