package com.sd.products.data

import com.sd.products.domain.dto.Product
import com.sd.products.domain.dto.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun loadData(
        @Query("limit") limit: Int
    ): Response<Products>

    @GET("products/{id}")
    suspend fun loadCurrent(
        @Path("id") id: Int
    ): Response<Product>
}