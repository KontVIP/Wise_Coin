package com.kontvip.wisecoin.domain

import com.kontvip.wisecoin.domain.model.Payments

interface TransactionsInteractor {

    suspend fun fetchPaymentsData(
        onSuccess: suspend (Payments) -> Unit,
        onError: (Int) -> Unit
    )

    class Default(
        private val repository: Repository
    ) : TransactionsInteractor {
        override suspend fun fetchPaymentsData(
            onSuccess: suspend (Payments) -> Unit,
            onError: (Int) -> Unit
        ) {
            repository.fetchPaymentsData(onSuccess, onError)
        }
    }

}