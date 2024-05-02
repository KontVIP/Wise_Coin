package com.kontvip.wisecoin.data.model

import com.kontvip.wisecoin.data.core.IdProvide
import com.kontvip.wisecoin.data.core.IdRequest
import com.kontvip.wisecoin.domain.core.UiState

data class ClientInfo(
    private val clientId: String,
    private val name: String,
) : IdProvide, UiState<ClientInfo.Display> {

    override fun display(uiDisplay: Display) {
        uiDisplay.displayClientName(name)
    }

    override fun onIdRequested(idRequest: IdRequest) {
        idRequest.onIdProvided(clientId)
    }

    interface Display : UiState.UiDisplay {
        fun displayClientName(name: String)
    }

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(clientId, name)
    }

    interface Mapper<T> {
        fun map(id: String, name: String): T
    }
}