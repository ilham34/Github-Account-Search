package com.nugroho.githubexpert.core.utils

import com.nugroho.githubexpert.core.data.source.local.entity.UserEntity
import com.nugroho.githubexpert.core.data.source.remote.response.UserDetail
import com.nugroho.githubexpert.core.data.source.remote.response.UserResponseItem
import com.nugroho.githubexpert.core.domain.model.Detail
import com.nugroho.githubexpert.core.domain.model.Follow
import com.nugroho.githubexpert.core.domain.model.User

object DataMapper {
    fun mapResponsesToEntities(input: List<UserResponseItem>): List<UserEntity> {
        val userList = ArrayList<UserEntity>()
        input.map {
            val user = UserEntity(
                id = it.id,
                avatarUrl = it.avatarUrl,
                login = it.login,
                htmlUrl = it.htmlUrl,
                isFavorite = false
            )
            userList.add(user)
        }
        return userList
    }

    fun mapEntitiesToDomain (input: List<UserEntity>): List<User> =
        input.map {
            User(
                id = it.id,
                avatarUrl = it.avatarUrl,
                login = it.login,
                htmlUrl = it.htmlUrl,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: User) = UserEntity(
        id = input.id,
        avatarUrl = input.avatarUrl,
        login = input.login,
        htmlUrl = input.htmlUrl,
        isFavorite = input.isFavorite
    )

    fun mapDetailResponsesToDomain(input: UserDetail) = Detail(
        repos = input.repos,
        name = input.name ?: "No Name",
        login = input.login,
        avatarUrl = input.avatarUrl,
        company = input.company ?: "No Company",
        location = input.location ?: "No Location",
        followers = input.followers,
        following = input.following
    )

    fun mapFollowResponsesToDomain(input: List<UserResponseItem>): List<Follow> {
        val followList = ArrayList<Follow>()
        input.map {
            val follow = Follow(
                id = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl
            )
            followList.add(follow)
        }
        return followList
    }
}