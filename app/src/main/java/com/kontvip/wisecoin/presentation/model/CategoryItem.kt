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

    fun getTotalExpenses(): Double {
        var expenses = 0.0
        payments.forEach {
            val cost = it.getCost()
            if (cost < 0) {
                expenses += cost
            }
        }
        return expenses
    }

    fun getTotalIncomes(): Double {
        var incomes = 0.0
        payments.forEach {
            val cost = it.getCost()
            if (cost >= 0) {
                incomes += cost
            }
        }
        return incomes
    }

    fun getTotalCost(): Double = payments.sumOf { it.getCost() }

    fun provideName(receiveName: ReceiveName) {
        receiveName.onNameReceived(name)
    }

    interface Display : UiState.UiDisplay {
        fun displayCategoryName(name: String)
        fun displayCost(cost: String)
    }

}