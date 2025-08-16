package com.example.lpl.di

import com.example.lpl.data.remote.api.ClientAPI
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

        val httpClientBuilder = OkHttpClient.Builder()

        //add this to log out user when token or response is not valid
        // httpClientBuilder.authenticator(TokenAuthenticator())
        httpClientBuilder.connectTimeout(90, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(90, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(90, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).client(httpClientBuilder.build())
            .build().create(serviceClass)
        return retrofit
    }
}