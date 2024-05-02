package com.kontvip.wisecoin.data

import com.kontvip.wisecoin.data.cache.CacheSource
import com.kontvip.wisecoin.data.cloud.CloudSource
import com.kontvip.wisecoin.data.model.ClientInfo
import com.kontvip.wisecoin.data.model.DefaultMonobankToken
import com.kontvip.wisecoin.data.model.PaymentData
import com.kontvip.wisecoin.domain.MonobankToken
import com.kontvip.wisecoin.domain.Repository
import com.kontvip.wisecoin.domain.core.ServerResult
import com.kontvip.wisecoin.domain.model.PaymentDomain
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class DefaultRepository(
    private val cacheSource: CacheSource,
    private val cloudSource: CloudSource
) : Repository {

    companion object {
        private const val REPEAT_REQUEST_DELAY = 5000L
        private const val MONTH_IN_DAYS = 28L
    }

    override fun saveMonobankToken(token: String) {
        cacheSource.saveMonobankToken(token)
    }

    override fun getMonobankToken(): MonobankToken {
        return DefaultMonobankToken(cacheSource.getMonobankToken())
    }

    override suspend fun fetchClientInfo(token: String): ServerResult<ClientInfo> {
        val clientInfo = cloudSource.fetchClientInfo(token)
        if (clientInfo.isSuccessful()) {
            cacheSource.saveClientInfo(clientInfo.extractData())
        } else {
            if (clientInfo.canRepeatRequest()) {
                delay(REPEAT_REQUEST_DELAY)
                return fetchClientInfo(token)
            }
            cacheSource.clearClientInfo()
        }
        return clientInfo
    }

    override suspend fun fetchCacheClientInfo(): ClientInfo {
        return cacheSource.fetchClientInfo()
    }

    override suspend fun fetchPayments(
        onPaymentReceived: suspend (List<PaymentDomain>) -> Unit, onError: (Int) -> Unit
    ) {
        val currentTime = System.currentTimeMillis()
        val result = cloudSource.fetchPayments(
            cacheSource.getMonobankToken(),
            currentTime - TimeUnit.DAYS.toMillis(MONTH_IN_DAYS),
            currentTime
        )
        if (result.isSuccessful()) {
            val paymentsData = result.extractData().toList()
            cacheSource.savePayments(paymentsData)
        } else {
            onError.invoke(result.errorResource())
        }
        onPaymentReceived.invoke(cacheSource.getAllPayments().map {
            it.map(object : PaymentData.Mapper<PaymentDomain> {
                override fun map(
                    id: String, time: Long, description: String, category: String, amount: Int
                ): PaymentDomain {
                    return PaymentDomain(id, time, description, category, amount)
                }
            })
        })
    }
}