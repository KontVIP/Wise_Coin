package com.kontvip.wisecoin.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kontvip.wisecoin.data.DefaultRepository
import com.kontvip.wisecoin.data.DefaultTokenServerValidator
import com.kontvip.wisecoin.data.cache.CacheSource
import com.kontvip.wisecoin.data.cache.WiseCoinSharedPreferences
import com.kontvip.wisecoin.data.cloud.CloudSource
import com.kontvip.wisecoin.data.cloud.api.MonobankApi
import com.kontvip.wisecoin.data.cloud.firebase.WiseCoinFirebase
import com.kontvip.wisecoin.data.cloud.mapper.ResponseClientInfoMapper
import com.kontvip.wisecoin.domain.Repository
import com.kontvip.wisecoin.domain.TokenServerValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.monobank.ua/personal/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideMonobankApi(retrofit: Retrofit): MonobankApi =
        retrofit.create(MonobankApi::class.java)

    @Provides
    @Singleton
    fun provideTokenValidator(repository: Repository): TokenServerValidator = DefaultTokenServerValidator(
        repository = repository
    )

    @Provides
    @Singleton
    fun provideRepository(cloudSource: CloudSource, cacheSource: CacheSource): Repository =
        DefaultRepository(
            cloudSource = cloudSource,
            cacheSource = cacheSource
        )

    @Provides
    @Singleton
    fun provideCloudSource(
        monobankApi: MonobankApi,
        wiseCoinFirebase: WiseCoinFirebase,
        responseClientInfoMapper: ResponseClientInfoMapper
    ): CloudSource =
        CloudSource.Default(
            monobankApi = monobankApi,
            wiseCoinFirebase = wiseCoinFirebase,
            responseClientInfoMapper = responseClientInfoMapper
        )

    @Provides
    @Singleton
    fun provideCacheSource(wiseCoinSharedPreferences: WiseCoinSharedPreferences): CacheSource =
        CacheSource.Default(
            wiseCoinSharedPreferences = wiseCoinSharedPreferences
        )

    @Provides
    @Singleton
    fun provideWiseCoinSharedPreferences(@ApplicationContext context: Context): WiseCoinSharedPreferences =
        WiseCoinSharedPreferences.Default(
            context = context
        )

    @Provides
    @Singleton
    fun provideResponseClientInfoMapper(): ResponseClientInfoMapper =
        ResponseClientInfoMapper.Default()

    @Provides
    @Singleton
    fun provideWiseCoinFirebase(): WiseCoinFirebase = WiseCoinFirebase.Default()
}