package com.nugroho.githubexpert.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nugroho.githubexpert.core.domain.usecase.UserUseCase

class FavoriteViewModel(userUseCase: UserUseCase) : ViewModel() {
    val favoriteUser = userUseCase.getFavoriteUser().asLiveData()
}