package com.kontvip.wisecoin.data.cloud.api

import com.kontvip.wisecoin.domain.model.ClientInfo
import com.kontvip.wisecoin.data.model.PaymentData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface MonobankApi {

    @Headers("Content-Type: application/json")
    @GET("client-info")
    suspend fun fetchClientInfo(@Header("X-Token") token: String): Response<ClientInfo>

    @Headers("Content-Type: application/json")
    @GET("statement/0/{from}/{to}")
    suspend fun fetchPaymentsData(
        @Header("X-Token") token: String,
        @Path("from") from: Long,
        @Path("to") to: Long
    ): Response<Array<PaymentData>>
}