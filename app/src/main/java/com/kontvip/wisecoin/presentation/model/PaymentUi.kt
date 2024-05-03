package com.kontvip.wisecoin.presentation.model

import com.kontvip.wisecoin.domain.core.PaymentUiState
import com.kontvip.wisecoin.domain.display.PaymentDisplay

class PaymentUi(
    private val id: String,
    private val time: Long,
    private val description: String,
    private val category: String,
    private val amount: Double,
    private val image: String
) : PaymentUiState {

    override fun isInCategory(category: String): Boolean {
        return this.category == category
    }

    override fun display(uiDisplay: PaymentDisplay) {
        uiDisplay.displayTime(time)
        uiDisplay.displayDescription(description)
        uiDisplay.displayCategory(category)
        uiDisplay.displayAmount(amount)
        uiDisplay.displayImage(image)
    }

    fun getCategory(): String = category

    fun getCost(): Double = amount

}