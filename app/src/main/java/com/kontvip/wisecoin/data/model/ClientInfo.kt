package com.kontvip.wisecoin.data.model

import com.kontvip.wisecoin.data.core.IdProvide
import com.kontvip.wisecoin.data.core.IdRequest

data class ClientInfo(
    private val clientId: String,
    private val name: String,
) : IdProvide {
    override fun onIdRequested(idRequest: IdRequest) {
        idRequest.onIdProvided(clientId)
    }
}