package com.nugroho.githubexpert.core.di

import com.google.gson.GsonBuilder
import com.nugroho.githubexpert.core.BuildConfig
import com.nugroho.githubexpert.core.data.source.remote.network.ApiService
import com.nugroho.githubexpert.core.data.source.remote.response.DetailInstance
import com.nugroho.githubexpert.core.data.source.remote.response.UserResponse
import com.nugroho.githubexpert.core.data.source.remote.response.ResponseInstance
import com.nugroho.githubexpert.core.data.source.remote.response.ResponseItemInstance
import com.nugroho.githubexpert.core.data.source.remote.response.UserDetail
import com.nugroho.githubexpert.core.data.source.remote.response.UserResponseItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val hostname = "api.github.com"
    private val certificatePinner = CertificatePinner.Builder()
        .add(hostname, "sha256/Jg78dOE+fydIGk19swWwiypUSR6HWZybfnJG/8G7pyM=")
        .add(hostname, "sha256/e0IRz5Tio3GA1Xs4fUVWmH1xHDiH2dMbVtCBSkOIdqM=")
        .add(hostname, "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=")
        .build()

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "token ${BuildConfig.API_KEY}")
                    .build()

                chain.proceed(request)
            }
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val gson = GsonBuilder()
            .registerTypeAdapter(UserResponse::class.java, ResponseInstance())
            .registerTypeAdapter(UserResponseItem::class.java, ResponseItemInstance())
            .registerTypeAdapter(UserDetail::class.java, DetailInstance())
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}