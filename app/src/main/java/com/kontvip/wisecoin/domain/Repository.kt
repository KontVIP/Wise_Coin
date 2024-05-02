package com.kontvip.wisecoin.domain

import com.kontvip.wisecoin.data.model.ClientInfo
import com.kontvip.wisecoin.domain.core.ServerResult
import com.kontvip.wisecoin.domain.model.PaymentDomain

interface Repository {
    fun saveMonobankToken(token: String)
    fun getMonobankToken(): MonobankToken
    suspend fun fetchClientInfo(token: String): ServerResult<ClientInfo>
    suspend fun fetchCacheClientInfo(): ClientInfo
    suspend fun fetchPayments(
        onPaymentReceived: suspend (List<PaymentDomain>) -> Unit, onError: (Int) -> Unit
    )
}