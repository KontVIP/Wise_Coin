package com.kontvip.wisecoin.domain.core

import com.kontvip.wisecoin.domain.display.PaymentDisplay

interface PaymentUiState : UiState<PaymentDisplay> {
    fun isInCategory(category: String): Boolean
}