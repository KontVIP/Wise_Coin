package com.kontvip.wisecoin.domain.core

import com.kontvip.wisecoin.domain.TokenServerValidator


interface MonobankToken {
    suspend fun isAcceptableForServer(tokenServerValidator: TokenServerValidator): Boolean

    fun isValid(): Boolean
}