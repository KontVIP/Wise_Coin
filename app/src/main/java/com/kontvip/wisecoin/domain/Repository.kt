package com.kontvip.wisecoin.domain

import com.kontvip.wisecoin.data.model.ClientInfo

interface Repository {
    fun saveMonobankToken(token: String)
    fun getMonobankToken(): MonobankToken
    suspend fun fetchCloudClientInfo(token: String): ClientInfo
    suspend fun fetchCacheClientInfo(): ClientInfo
    suspend fun shouldFetchDataFromFirebase(): Boolean
}