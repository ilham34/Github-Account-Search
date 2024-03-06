package com.nugroho.githubexpert.core.domain.model

data class Detail(
    val repos: Int,
    val avatarUrl: String,
    val login: String,
    val name: String? = null,
    val followers: Int,
    val following: Int,
    val location: String? = null,
    val company: String? = null
)
