package com.nugroho.githubexpert.core.data.source.remote.network

import com.nugroho.githubexpert.core.data.source.remote.response.UserDetail
import com.nugroho.githubexpert.core.data.source.remote.response.UserResponse
import com.nugroho.githubexpert.core.data.source.remote.response.UserResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String
    ): UserResponse

    @GET("users/{username}")
    suspend fun detailUser(
        @Path("username") username: String
    ): UserDetail

    @GET("users/{username}/{type}")
    suspend fun getFollow(
        @Path("username") username: String,
        @Path("type") type: String
    ): ArrayList<UserResponseItem>
}