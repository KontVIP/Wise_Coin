package com.kontvip.wisecoin.data.model

import com.kontvip.wisecoin.data.core.IdProvide
import com.kontvip.wisecoin.data.core.IdRequest

data class MCC(
    private val id: Int,
    private val description: String
) : IdProvide {
    fun isTheSame(otherMccDescription: String): Boolean {
        return otherMccDescription == description
    }

    override fun onIdRequested(idRequest: IdRequest) {
        idRequest.onIdProvided(id.toString())
    }
}