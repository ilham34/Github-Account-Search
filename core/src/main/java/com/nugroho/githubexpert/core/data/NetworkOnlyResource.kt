package com.nugroho.githubexpert.core.data

import com.nugroho.githubexpert.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkOnlyResource<RequestType, ResultType> {

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract fun mapRequestToResult(requestData: RequestType): ResultType

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())

        when (val response = createCall().first()) {
            is ApiResponse.Success -> {
                val resultData = mapRequestToResult(response.data)
                emit(Resource.Success(resultData))
            }
            is ApiResponse.Empty -> emit(Resource.Success(null as ResultType))
            is ApiResponse.Error -> emit(Resource.Error(response.errorMessage))
        }
    }

    fun asFlow(): Flow<Resource<ResultType>> = result
}

