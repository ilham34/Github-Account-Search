package com.nugroho.githubexpert.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "html_url")
    val htmlUrl: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
