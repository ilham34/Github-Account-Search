package com.nugroho.githubexpert.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("items")
    val items: ArrayList<UserResponseItem>
)