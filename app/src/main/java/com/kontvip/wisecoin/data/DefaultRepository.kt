package com.kontvip.wisecoin.data

import com.kontvip.wisecoin.data.cache.CacheSource
import com.kontvip.wisecoin.data.cloud.CloudSource
import com.kontvip.wisecoin.domain.model.ClientInfo
import com.kontvip.wisecoin.data.model.DefaultMonobankToken
import com.kontvip.wisecoin.data.model.PaymentData
import com.kontvip.wisecoin.domain.core.MonobankToken
import com.kontvip.wisecoin.domain.core.Repository
import com.kontvip.wisecoin.domain.model.TransactionPeriod
import com.kontvip.wisecoin.domain.core.ServerResult
import com.kontvip.wisecoin.domain.model.Currency
import com.kontvip.wisecoin.domain.model.PaymentDomain
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class DefaultRepository(
    private val cacheSource: CacheSource,
    private val cloudSource: CloudSource
) : Repository {

    companion object {
        private const val REPEAT_REQUEST_DELAY = 5000L
        private const val MONTH_IN_DAYS = 30L
    }

    override fun saveMonobankToken(token: String) {
        cacheSource.saveMonobankToken(token)
    }

    override fun getMonobankToken(): MonobankToken {
        return DefaultMonobankToken(cacheSource.getMonobankToken())
    }

    override fun saveIsSkippedMonobankAuth(isSkipped: Boolean) {
        cacheSource.saveIsSkippedMonobankAuth(isSkipped)
    }

    override fun wasMonobankAuthSkipped(): Boolean {
        return cacheSource.wasMonobankAuthSkipped()
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

    override suspend fun fetchCachedClientInfo(): ClientInfo {
        return cacheSource.fetchClientInfo()
    }

    override suspend fun fetchCachedPayments(period: TransactionPeriod): List<PaymentDomain> {
        return cacheSource.getAllPayments(period).map { it.map(object : PaymentData.Mapper<PaymentDomain> {
            override fun map(
                id: String, time: Long, description: String, category: String, amount: Double, image: String
            ): PaymentDomain {
                return PaymentDomain(id, time, description, category, amount, image)
            }
        }) }
    }

    override suspend fun savePayment(payment: PaymentDomain) {
        cacheSource.savePayment(payment.map(object : PaymentDomain.Mapper<PaymentData> {
            override fun map(
                id: String, time: Long, description: String, category: String, amount: Double, image: String
            ): PaymentData {
                return PaymentData(id, time, description, category, amount, image)
            }
        }))
    }

    override fun saveUserCurrency(currency: Currency) {
        cacheSource.saveUserCurrency(currency)
    }

    override fun getUserCurrency(): Currency {
        return cacheSource.getUserCurrency()
    }

    override fun deleteTransaction(id: String) {
        cacheSource.deleteTransaction(id)
    }

    override suspend fun fetchPayments(
        onSuccess: suspend (List<PaymentDomain>) -> Unit, onError: (Int) -> Unit
    ) {
        fetchCloudPayments(onSuccess = {
            cacheSource.savePayments(it)
            onSuccess.invoke(fetchCachedPayments(TransactionPeriod.Year))
        }, onError = onError)
    }

    private suspend fun fetchCloudPayments(
        alreadyFetchedPayments: MutableList<PaymentData> = mutableListOf(),
        onSuccess: suspend (List<PaymentData>) -> Unit, onError: (Int) -> Unit,
        toPeriod: Long = System.currentTimeMillis()
    ) {
        val from = toPeriod - TimeUnit.DAYS.toMillis(MONTH_IN_DAYS)
        val result = cloudSource.fetchPayments(cacheSource.getMonobankToken(), from, toPeriod)

        if (result.isSuccessful()) {
            alreadyFetchedPayments.addAll(result.extractData().toList())
            fetchCloudPayments(alreadyFetchedPayments, onSuccess, onError, toPeriod = from)
        } else {
            if (alreadyFetchedPayments.isNotEmpty()) {
                onSuccess.invoke(alreadyFetchedPayments)
            } else {
                onError.invoke(result.errorResource())
            }
        }
    }
}