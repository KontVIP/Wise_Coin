package com.kontvip.wisecoin.domain

import com.kontvip.wisecoin.data.model.ClientInfo
import com.kontvip.wisecoin.domain.core.ServerResult
import com.kontvip.wisecoin.domain.core.UiState
import com.kontvip.wisecoin.domain.display.PaymentsDisplay
import com.kontvip.wisecoin.domain.model.Payments

interface Repository {
    fun saveMonobankToken(token: String)
    fun getMonobankToken(): MonobankToken
    suspend fun fetchClientInfo(token: String): ServerResult<ClientInfo>
    suspend fun fetchCacheClientInfo(): ClientInfo
    suspend fun shouldFetchDataFromFirebase(): Boolean
    suspend fun fetchPaymentsData(onSuccess: (Payments) -> Unit, onError: (Int) -> Unit)
}