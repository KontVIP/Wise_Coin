package com.kontvip.wisecoin.data.cloud.api

import com.kontvip.wisecoin.data.model.ClientInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MonobankApi {

    @Headers("Content-Type: application/json")
    @GET("client-info")
    suspend fun fetchClientInfo(@Header("X-Token") token: String): Response<ClientInfo.DefaultClientInfo>
}