package com.kontvip.wisecoin.domain


interface MonobankToken {
    suspend fun isAcceptableForServer(tokenServerValidator: TokenServerValidator): Boolean

    fun isValid(): Boolean
}