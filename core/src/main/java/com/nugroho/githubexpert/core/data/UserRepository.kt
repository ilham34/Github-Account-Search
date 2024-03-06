package com.nugroho.githubexpert.core.data

import com.nugroho.githubexpert.core.data.source.local.LocalDataSource
import com.nugroho.githubexpert.core.data.source.remote.RemoteDataSource
import com.nugroho.githubexpert.core.data.source.remote.network.ApiResponse
import com.nugroho.githubexpert.core.data.source.remote.response.UserDetail
import com.nugroho.githubexpert.core.data.source.remote.response.UserResponseItem
import com.nugroho.githubexpert.core.domain.model.Detail
import com.nugroho.githubexpert.core.domain.model.Follow
import com.nugroho.githubexpert.core.domain.model.User
import com.nugroho.githubexpert.core.domain.repository.IUserRepository
import com.nugroho.githubexpert.core.utils.AppExecutors
import com.nugroho.githubexpert.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IUserRepository {

    override fun getSearchUser(q: String): Flow<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponseItem>>() {
            override fun loadFromDB(): Flow<List<User>> {
                val query = "%$q%"
                return localDataSource.getSearchUser(query).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponseItem>>> =
                remoteDataSource.getSearchUser(q)

            override suspend fun saveCallResult(data: List<UserResponseItem>) {
                val userList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertUser(userList)
            }

            override fun shouldFetch(data: List<User>?): Boolean =
                data.isNullOrEmpty()
        }.asFlow()

    override fun getFollow(username: String, type: String): Flow<Resource<List<Follow>>> =
        object : NetworkOnlyResource<List<UserResponseItem>, List<Follow>>() {
            override suspend fun createCall(): Flow<ApiResponse<List<UserResponseItem>>> =
                remoteDataSource.getFollow(username, type)

            override fun mapRequestToResult(requestData: List<UserResponseItem>): List<Follow> =
                DataMapper.mapFollowResponsesToDomain(requestData)
        }.asFlow()


    override fun getDetail(username: String): Flow<Resource<Detail>> =
        object : NetworkOnlyResource<UserDetail, Detail>() {
            override suspend fun createCall(): Flow<ApiResponse<UserDetail>> =
                remoteDataSource.getDetail(username)

            override fun mapRequestToResult(requestData: UserDetail): Detail =
                DataMapper.mapDetailResponsesToDomain(requestData)
        }.asFlow()


    override fun getFavoriteUser(): Flow<List<User>> {
        return localDataSource.getFavoriteUser().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavorite(user: User, state: Boolean) {
        val userEntity = DataMapper.mapDomainToEntity(user)
        appExecutors.diskIO().execute { localDataSource.setFavoriteUser(userEntity, state) }
    }

}