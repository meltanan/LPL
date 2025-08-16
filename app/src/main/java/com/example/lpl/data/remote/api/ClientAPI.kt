package com.example.lpl.data.remote.api

import com.example.lpl.domian.model.Client
import retrofit2.Response
import retrofit2.http.GET

interface ClientAPI {
    @GET("posts/1/comments")
    suspend fun getClients(): Response<List<Client>>
}