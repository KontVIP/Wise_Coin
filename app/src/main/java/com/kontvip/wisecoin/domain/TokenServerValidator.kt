package com.kontvip.wisecoin.domain

interface TokenServerValidator {
    suspend fun isValid(token: String): Boolean
}