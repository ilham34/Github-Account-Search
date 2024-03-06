package com.nugroho.githubexpert.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val avatarUrl: String,
    val login: String,
    val htmlUrl: String,
    val isFavorite: Boolean = false
) : Parcelable
