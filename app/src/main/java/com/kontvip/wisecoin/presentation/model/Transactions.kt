package com.kontvip.wisecoin.presentation.model

import java.util.ArrayList

class Transactions : ArrayList<TransactionItem>() {
    fun calculateCost(): String {
        val sum = getTotalCost()
        val dollars = (sum / 100).toInt()
        val cents = sum % 100
        return "$$dollars,${cents.toString().padStart(2, '0')}"
    }

    fun getTotalCost(): Long = sumOf { it.getCost() }
}