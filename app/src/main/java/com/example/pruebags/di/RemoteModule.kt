package com.example.pruebags.di

import com.example.pruebags.data.service.GSApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .build()

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient) :  GSApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://akabab.github.io/starwars-api/")
            .client(okHttpClient)
            .build()
            .create(GSApi::class.java)
    }


}