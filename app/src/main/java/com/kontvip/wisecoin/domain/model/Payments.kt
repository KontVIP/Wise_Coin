package com.kontvip.wisecoin.domain.model

import com.kontvip.wisecoin.domain.core.PaymentUiState

class Payments(
    payments: List<PaymentUiState> = arrayListOf()
) : ArrayList<PaymentUiState>(payments) {

    fun filterByCategory(category: String): Payments {
        return Payments(filter { it.isInCategory(category) })
    }
}