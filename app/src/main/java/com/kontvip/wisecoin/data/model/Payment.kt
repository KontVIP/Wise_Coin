package com.kontvip.wisecoin.data.model

import com.kontvip.wisecoin.domain.core.PaymentUiState
import com.kontvip.wisecoin.domain.display.PaymentDisplay

data class Payment(
    private val id: String,
    private val time: Long,
    private val description: String,
    private val mcc: MCC,
    private val amount: Int
) : PaymentUiState {
    override fun isInCategory(category: String): Boolean {
        return mcc.isTheSame(category)
    }

    override fun display(uiDisplay: PaymentDisplay) {
        uiDisplay.displayTime(time)
        uiDisplay.displayDescription(description)
        uiDisplay.displayMcc(mcc)
        uiDisplay.displayAmount(amount)
    }

}
