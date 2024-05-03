package com.kontvip.wisecoin.presentation.model

import com.kontvip.wisecoin.domain.core.UiState

data class CategoryItem(
    private val name: String,
    private val payments: List<PaymentUi>
) : UiState<CategoryItem.Display> {

    override fun display(uiDisplay: Display) {
        uiDisplay.displayCategoryName(name)
        uiDisplay.displayCost((getTotalCost() / 100).toString())
    }

    fun getTotalCost(): Double = payments.sumOf { it.getCost() }

    interface Display : UiState.UiDisplay {
        fun displayCategoryName(name: String)
        fun displayCost(cost: String)
    }

}