package com.kontvip.wisecoin.domain.core

interface UiState<T: UiState.UiDisplay> {
    fun display(uiDisplay: T)

    interface UiDisplay
}