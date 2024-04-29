package com.kontvip.wisecoin.domain.display

import com.kontvip.wisecoin.domain.core.UiState

interface PaymentsDisplay : UiState.UiDisplay {
    fun displayPayment(display: PaymentDisplay, position: Int)
}