package com.kontvip.wisecoin.domain

import com.kontvip.wisecoin.domain.model.Currency
import com.kontvip.wisecoin.domain.model.PaymentDomain

interface TransactionsInteractor {

    suspend fun <T> fetchPayments(
        paymentMapper: PaymentDomain.Mapper<T>,
        onSuccess: suspend (List<T>) -> Unit,
        onError: (Int) -> Unit
    )
    suspend fun updatePaymentsFromCloud(onUpdateFinished: () -> Unit)
    suspend fun <T> fetchCachedPayments(
        period: TransactionPeriod,
        paymentMapper: PaymentDomain.Mapper<T>,
    ): List<T>
    suspend fun savePayment(payment: PaymentDomain)
    fun saveUserCurrency(currency: Currency)
    fun getUserCurrency(): Currency

    class Default(
        private val repository: Repository
    ) : TransactionsInteractor {
        override suspend fun <T> fetchPayments(
            paymentMapper: PaymentDomain.Mapper<T>,
            onSuccess: suspend (List<T>) -> Unit,
            onError: (Int) -> Unit
        ) {
            repository.fetchPayments(
                onSuccess = {
                    onSuccess.invoke(it.map { payment -> payment.map(paymentMapper) })
                },
                onError = onError
            )
        }

        override suspend fun updatePaymentsFromCloud(onUpdateFinished: () -> Unit) {
            repository.fetchPayments(
                onSuccess = {
                    onUpdateFinished.invoke()
                },
                onError = {
                    onUpdateFinished.invoke()
                }
            )
        }

        override suspend fun <T> fetchCachedPayments(
            period: TransactionPeriod,
            paymentMapper: PaymentDomain.Mapper<T>
        ): List<T> {
            return repository.fetchCachedPayments(period).map { it.map(paymentMapper) }
        }

        override suspend fun savePayment(payment: PaymentDomain) {
            repository.savePayment(payment)
        }

        override fun saveUserCurrency(currency: Currency) {
            repository.saveUserCurrency(currency)
        }

        override fun getUserCurrency(): Currency {
            return repository.getUserCurrency()
        }
    }

}