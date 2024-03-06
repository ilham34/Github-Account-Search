package com.nugroho.githubexpert.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nugroho.githubexpert.core.domain.model.User
import com.nugroho.githubexpert.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun getDetailUser(login: String) = userUseCase.getDetail(login).asLiveData()
    fun setFavoriteUser(user: User, state: Boolean) = userUseCase.setFavoriteUser(user, state)
}