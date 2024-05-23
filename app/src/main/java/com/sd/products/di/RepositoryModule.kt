package com.sd.products.di

import com.sd.products.data.ProductRepositoryImpl
import com.sd.products.domain.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindsRepository(impl: ProductRepositoryImpl): ProductRepository
}