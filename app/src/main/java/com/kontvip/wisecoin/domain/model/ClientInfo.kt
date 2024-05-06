package com.kontvip.wisecoin.domain.model

import com.kontvip.wisecoin.domain.core.UiState

data class ClientInfo(
    private val clientId: String,
    private val name: String,
) : UiState<ClientInfo.Display> {

    override fun display(uiDisplay: Display) {
        uiDisplay.displayClientName(name)
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