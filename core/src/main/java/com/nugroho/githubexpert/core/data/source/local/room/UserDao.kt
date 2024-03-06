package com.nugroho.githubexpert.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nugroho.githubexpert.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<UserEntity>)

    @Query("SELECT * FROM github_user WHERE login LIKE :q")
    fun getSearchUser(q: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM github_user WHERE isFavorite = 1")
    fun getFavoriteUser(): Flow<List<UserEntity>>

    @Update
    fun updateFavoriteUser(user: UserEntity)
}