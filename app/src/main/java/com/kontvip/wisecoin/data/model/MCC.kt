package com.kontvip.wisecoin.data.model

data class MCC(
    private val id: Int,
    private val description: String
) {
    fun isTheSame(otherMccDescription: String): Boolean {
        return otherMccDescription == description
    }

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(id, description)
    }

    interface Mapper<T> {
        fun  map(id: Int, description: String): T
    }
}