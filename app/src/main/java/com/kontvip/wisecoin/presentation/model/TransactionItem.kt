package com.kontvip.wisecoin.presentation.model

import com.kontvip.wisecoin.domain.model.UiState

data class TransactionItem(
    private val id: String,
    private val name: String,
    private val cost: Long,
    private val imageUrl: String
) : UiState {

    fun getCost(): Long = cost

}