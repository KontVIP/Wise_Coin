package com.kontvip.wisecoin.domain


interface MonobankToken {
    suspend fun isValid(tokenValidator: TokenValidator): Boolean
}