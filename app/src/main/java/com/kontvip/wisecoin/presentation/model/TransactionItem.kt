package com.kontvip.wisecoin.presentation.model

import com.kontvip.wisecoin.domain.core.UiState

data class TransactionItem(
    private val id: String,
    private val name: String,
    private val cost: Long,
    private val imageUrl: String
) : UiState<TransactionItem.Display> {

    fun getCost(): Long = cost

    override fun display(uiDisplay: Display) {
        uiDisplay.displayName(name)
        uiDisplay.displayCost(cost)
        uiDisplay.displayImage(imageUrl)
    }

    interface Display: UiState.UiDisplay {
        fun displayName(name: String)
        fun displayCost(cost: Long)
        fun displayImage(imageUrl: String)
    }
}