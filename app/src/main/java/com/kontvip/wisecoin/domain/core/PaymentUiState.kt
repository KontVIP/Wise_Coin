package com.kontvip.wisecoin.domain.core

import com.kontvip.wisecoin.data.core.IdProvide
import com.kontvip.wisecoin.domain.display.PaymentDisplay

interface PaymentUiState : UiState<PaymentDisplay>, IdProvide {
    fun isInCategory(category: String): Boolean
}