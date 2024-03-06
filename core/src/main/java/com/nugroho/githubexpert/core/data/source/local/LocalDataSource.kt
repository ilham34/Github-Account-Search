package com.nugroho.githubexpert.core.data.source.local

import com.nugroho.githubexpert.core.data.source.local.entity.UserEntity
import com.nugroho.githubexpert.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val userDao: UserDao) {

    fun getSearchUser(q: String): Flow<List<UserEntity>> = userDao.getSearchUser(q)

    fun getFavoriteUser(): Flow<List<UserEntity>> = userDao.getFavoriteUser()

    suspend fun insertUser(userList: List<UserEntity>) = userDao.insertUser(userList)

    fun setFavoriteUser(user: UserEntity, newState: Boolean) {
        user.isFavorite = newState
        userDao.updateFavoriteUser(user)
    }
}