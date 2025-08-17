package com.example.lpl.di

import com.example.lpl.data.remote.api.ClientAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideClientAPI(): ClientAPI {
        return createService(
            ClientAPI::class.java
        )
    }

    @Singleton
    fun <S> createService(serviceClass: Class<S>): S {
        val baseURL = "https://jsonplaceholder.typicode.com/"
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        val httpClientBuilder = OkHttpClient.Builder()

        httpClientBuilder.addInterceptor(interceptor)
        httpClientBuilder.connectTimeout(90, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(90, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClientBuilder.build())
            .build().create(serviceClass)
        return retrofit
    }
}