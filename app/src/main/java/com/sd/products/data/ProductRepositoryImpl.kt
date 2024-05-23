package com.sd.products.data

import com.sd.products.domain.ProductRepository
import com.sd.products.domain.dto.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : ProductRepository {

    override suspend fun getCurrentProduct(id: Int): Product {
        try {
            val response = apiService.loadCurrent(id)
            if (!response.isSuccessful) {
                return Product()
            }
            val body = response.body()
            return body ?: Product()
        } catch (e: Exception) {
            e.printStackTrace()
            return Product()
        }
    }

    override suspend fun getListProducts(): List<Product> {
        try {
            val response = apiService.loadData(LIMIT)
            if (!response.isSuccessful) {
                return listOf()
            }
            val body = response.body()
            return body?.products ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    companion object {
        const val LIMIT = 0
    }
}