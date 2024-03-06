package com.nugroho.githubexpert.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponseItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("html_url")
    val htmlUrl: String
) {
    // Default no-args constructor
    constructor() : this(0, "", "", "")
}
