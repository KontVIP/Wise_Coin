package com.kontvip.wisecoin.data.model

class Payments : ArrayList<Payment>()

data class Payment(
    private val id: String,
    private val time: Long,
    private val description: String,
    private val mcc: Int,
    private val amount: Int
)