package com.kontvip.wisecoin.presentation.model

import com.kontvip.wisecoin.databinding.CategoryItemBinding
import com.kontvip.wisecoin.domain.core.UiState

data class CategoryItem(
    private val id: String,
    private val name: String,
    private val imageUrl: String,
    private val transactions: Transactions
) : UiState<CategoryItem.Display> {

    fun display(binding: CategoryItemBinding) {
        binding.categoryNameTextView.text = name
        binding.costTextView.text = transactions.calculateCost()
    }

    override fun display(uiDisplay: Display) {
        uiDisplay.displayCategoryName(name)
        uiDisplay.displayImage(imageUrl)
        uiDisplay.displayCost(transactions.calculateCost())
    }

    fun getTotalCost(): Long = transactions.getTotalCost()

    interface Display : UiState.UiDisplay {
        fun displayCategoryName(name: String)
        fun displayImage(imageUrl: String)
        fun displayCost(cost: String)
    }

}