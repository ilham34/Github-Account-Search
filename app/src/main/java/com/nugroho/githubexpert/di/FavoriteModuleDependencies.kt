package com.nugroho.githubexpert.di

import com.nugroho.githubexpert.core.domain.usecase.UserUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun userUseCase(): UserUseCase

}