package com.kontvip.wisecoin.presentation.model

import com.bumptech.glide.Glide
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.CategoryItemBinding
import com.kontvip.wisecoin.domain.model.UiState

data class CategoryItem(
    private val id: String,
    private val name: String,
    private val imageUrl: String,
    private val transactions: Transactions
) : UiState {

    fun display(binding: CategoryItemBinding) {
        binding.categoryNameTextView.text = name
        binding.costTextView.text = transactions.calculateCost()

        if (imageUrl.isNotBlank()) {
            Glide.with(binding.categoryIconImageView).load(imageUrl)
                .into(binding.categoryIconImageView)
        } else {
            //todo: handle the icon
            binding.categoryIconImageView.setImageResource(R.drawable.ic_home)
        }
    }

    fun getTotalCost(): Long = transactions.getTotalCost()

}