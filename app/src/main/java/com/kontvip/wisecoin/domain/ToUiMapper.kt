package com.kontvip.wisecoin.domain

import com.kontvip.wisecoin.domain.model.UiState

interface ToUiMapper {
    fun map(): UiState
}