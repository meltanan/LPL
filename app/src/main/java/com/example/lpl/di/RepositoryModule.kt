package com.example.lpl.di

import com.example.lpl.data.repositoryImplementation.ClientRepositoryImpl
import com.example.lpl.domian.repository.ClientRepository
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
    abstract fun bindClientRepository(clientRepositoryImpl: ClientRepositoryImpl): ClientRepository
}