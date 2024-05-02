package com.kontvip.wisecoin.domain

import com.kontvip.wisecoin.domain.model.PaymentDomain

interface TransactionsInteractor {

    suspend fun <T> fetchPaymentsData(
        paymentMapper: PaymentDomain.Mapper<T>,
        onPaymentReceived: suspend (List<T>) -> Unit,
        onError: (Int) -> Unit
    )

    class Default(
        private val repository: Repository
    ) : TransactionsInteractor {
        override suspend fun <T> fetchPaymentsData(
            paymentMapper: PaymentDomain.Mapper<T>,
            onPaymentReceived: suspend (List<T>) -> Unit,
            onError: (Int) -> Unit
        ) {
            repository.fetchPayments(
                onPaymentReceived = {
                    onPaymentReceived.invoke(it.map { payment -> payment.map(paymentMapper) })
                },
                onError = onError
            )
        }
    }

}