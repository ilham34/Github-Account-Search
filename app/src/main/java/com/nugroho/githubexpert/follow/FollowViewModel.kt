package com.nugroho.githubexpert.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nugroho.githubexpert.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun getFollow(login: String, type: String) = userUseCase.getFollow(login, type).asLiveData()
}