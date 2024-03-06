package com.nugroho.githubexpert.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nugroho.githubexpert.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun getSearchUser(q: String) = userUseCase.getSearchUser(q).asLiveData()
}