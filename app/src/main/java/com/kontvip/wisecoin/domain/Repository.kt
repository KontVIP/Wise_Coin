package com.kontvip.wisecoin.domain

import com.kontvip.wisecoin.data.model.ClientInfo
import com.kontvip.wisecoin.domain.model.ServerResult

interface Repository {
    fun saveMonobankToken(token: String)
    fun getMonobankToken(): MonobankToken
    suspend fun fetchClientInfo(token: String): ServerResult<ClientInfo>
    suspend fun fetchCacheClientInfo(): ClientInfo
    suspend fun shouldFetchDataFromFirebase(): Boolean
    suspend fun fetchPaymentsData(): ServerResult<*>
}