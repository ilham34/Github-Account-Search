package com.nugroho.githubexpert.core.domain.repository

import com.nugroho.githubexpert.core.data.Resource
import com.nugroho.githubexpert.core.domain.model.Detail
import com.nugroho.githubexpert.core.domain.model.Follow
import com.nugroho.githubexpert.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getSearchUser(q: String): Flow<Resource<List<User>>>
    fun getFollow(username: String, type: String): Flow<Resource<List<Follow>>>
    fun getDetail(username: String): Flow<Resource<Detail>>
    fun getFavoriteUser(): Flow<List<User>>
    fun setFavorite(user: User, state: Boolean)
}