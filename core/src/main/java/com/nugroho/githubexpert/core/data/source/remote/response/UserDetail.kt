package com.nugroho.githubexpert.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetail(

    @field:SerializedName("public_repos")
    val repos: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("company")
    val company: String? = null
)


