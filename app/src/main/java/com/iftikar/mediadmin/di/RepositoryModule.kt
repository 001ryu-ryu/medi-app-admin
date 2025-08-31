package com.iftikar.mediadmin.di

import com.iftikar.mediadmin.data.repository.ProductRepositoryImpl
import com.iftikar.mediadmin.data.repository.UserRepositoryImpl
import com.iftikar.mediadmin.domain.repository.ProductRepository
import com.iftikar.mediadmin.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository
}