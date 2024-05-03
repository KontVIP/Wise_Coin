package com.kontvip.wisecoin.domain.model

data class PaymentDomain(
    private val id: String,
    private val time: Long,
    private val description: String,
    private val category: String,
    private val amount: Double,
    private val image: String
) {

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(id, time, description, category, amount, image)
    }

    interface Mapper<T> {
        fun map(id: String, time: Long, description: String, category: String, amount: Double, image: String): T
    }
}