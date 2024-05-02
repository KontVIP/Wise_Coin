package com.kontvip.wisecoin.domain

import com.kontvip.wisecoin.domain.model.PaymentDomain

interface TransactionsInteractor {

    suspend fun <T> fetchPayments(
        paymentMapper: PaymentDomain.Mapper<T>,
        onSuccess: suspend (List<T>) -> Unit,
        onError: (Int) -> Unit
    )

    suspend fun<T> fetchCachedPayments(paymentMapper: PaymentDomain.Mapper<T>, ): List<T>

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

        override suspend fun <T> fetchCachedPayments(paymentMapper: PaymentDomain.Mapper<T>): List<T> {
            return repository.fetchCachedPayments().map { it.map(paymentMapper) }
        }
    }

}