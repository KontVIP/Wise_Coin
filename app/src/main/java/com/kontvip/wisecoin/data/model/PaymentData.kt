package com.kontvip.wisecoin.data.model

data class PaymentData(
    private val id: String,
    private val time: Long,
    private val description: String,
    private val category: String,
    private val amount: Int
) {

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(id, time, description, category, amount)
    }

    interface Mapper<T> {
        fun map(id: String, time: Long, description: String, category: String, amount: Int): T
    }

}
