package com.kontvip.wisecoin.domain.core

import com.kontvip.wisecoin.domain.core.MonobankToken
import com.kontvip.wisecoin.domain.model.ClientInfo
import com.kontvip.wisecoin.domain.core.ServerResult
import com.kontvip.wisecoin.domain.model.Currency
import com.kontvip.wisecoin.domain.model.PaymentDomain
import com.kontvip.wisecoin.domain.model.TransactionPeriod

interface Repository {
    fun saveMonobankToken(token: String)
    fun getMonobankToken(): MonobankToken
    fun saveIsSkippedMonobankAuth(isSkipped: Boolean)
    fun wasMonobankAuthSkipped(): Boolean
    suspend fun fetchClientInfo(token: String): ServerResult<ClientInfo>
    suspend fun fetchCachedClientInfo(): ClientInfo
    suspend fun fetchPayments(
        onSuccess: suspend (List<PaymentDomain>) -> Unit, onError: (Int) -> Unit
    )
    suspend fun fetchCachedPayments(period: TransactionPeriod): List<PaymentDomain>
    suspend fun savePayment(payment: PaymentDomain)
    fun saveUserCurrency(currency: Currency)
    fun getUserCurrency(): Currency
    fun deleteTransaction(id: String)
}