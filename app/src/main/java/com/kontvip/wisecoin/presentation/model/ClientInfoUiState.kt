package com.kontvip.wisecoin.presentation.model

import com.kontvip.wisecoin.domain.core.UiState

class ClientInfoUiState(
    private val name: String
) : UiState<ClientInfoUiState.Display> {

    override fun display(uiDisplay: Display) {
        uiDisplay.displayClientName(name)
    }

    interface Display : UiState.UiDisplay {
        fun displayClientName(name: String)
    }
}