package com.kontvip.wisecoin.domain

interface TokenValidator {
    suspend fun isValid(token: String): Boolean
}