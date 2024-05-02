package com.kontvip.wisecoin.domain.model

data class PaymentDomain(
    private val id: String,
    private val time: Long,
    private val description: String,
    private val category: String,
    private val amount: Int,
    private val image: String
) {

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(id, time, description, category, amount, image)
    }

    interface Mapper<T> {
        fun map(id: String, time: Long, description: String, category: String, amount: Int, image: String): T
    }
}