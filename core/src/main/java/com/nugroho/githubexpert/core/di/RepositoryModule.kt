package com.nugroho.githubexpert.core.di

import com.nugroho.githubexpert.core.data.UserRepository
import com.nugroho.githubexpert.core.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(userRepository: UserRepository): IUserRepository

}