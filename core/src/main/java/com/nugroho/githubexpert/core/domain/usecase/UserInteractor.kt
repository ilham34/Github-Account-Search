package com.nugroho.githubexpert.core.domain.usecase

import com.nugroho.githubexpert.core.data.Resource
import com.nugroho.githubexpert.core.domain.model.Detail
import com.nugroho.githubexpert.core.domain.model.Follow
import com.nugroho.githubexpert.core.domain.model.User
import com.nugroho.githubexpert.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository): UserUseCase {

    override fun getSearchUser(q: String): Flow<Resource<List<User>>> = userRepository.getSearchUser(q)

    override fun getFollow(username: String, type: String): Flow<Resource<List<Follow>>> = userRepository.getFollow(username, type)

    override fun getDetail(username: String): Flow<Resource<Detail>> = userRepository.getDetail(username)

    override fun getFavoriteUser(): Flow<List<User>> = userRepository.getFavoriteUser()

    override fun setFavoriteUser(user: User, state: Boolean) = userRepository.setFavorite(user, state)

}