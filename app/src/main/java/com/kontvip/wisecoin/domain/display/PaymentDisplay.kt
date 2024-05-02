package com.kontvip.wisecoin.domain.display

import com.kontvip.wisecoin.data.model.MCC
import com.kontvip.wisecoin.domain.core.UiState

interface PaymentDisplay : UiState.UiDisplay {
    fun displayTime(time: Long)
    fun displayDescription(description: String)
    fun displayCategory(category: String)
    fun displayAmount(amount: Int)
}