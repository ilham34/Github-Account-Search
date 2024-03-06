package com.nugroho.githubexpert.core.data.source.remote

import android.util.Log
import com.nugroho.githubexpert.core.data.source.remote.network.ApiResponse
import com.nugroho.githubexpert.core.data.source.remote.network.ApiService
import com.nugroho.githubexpert.core.data.source.remote.response.UserDetail
import com.nugroho.githubexpert.core.data.source.remote.response.UserResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getSearchUser(query: String): Flow<ApiResponse<List<UserResponseItem>>> {
        return flow {
            try {
                val response = apiService.searchUser(query)
                val dataArray = response.items
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.items))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollow(
        username: String,
        type: String
    ): Flow<ApiResponse<ArrayList<UserResponseItem>>> {
        return flow {
            try {
                val response = apiService.getFollow(username, type)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetail(username: String): Flow<ApiResponse<UserDetail>> {
        return flow {
            try {
                val response = apiService.detailUser(username)
                if (response != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}