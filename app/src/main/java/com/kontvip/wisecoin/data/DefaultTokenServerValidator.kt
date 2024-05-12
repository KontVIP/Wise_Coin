package com.kontvip.wisecoin.data

import com.kontvip.wisecoin.domain.core.Repository
import com.kontvip.wisecoin.domain.TokenServerValidator

class DefaultTokenServerValidator(
    private val repository: Repository
) : TokenServerValidator {
    override suspend fun isValid(token: String): Boolean {
        val clientInfo = repository.fetchClientInfo(token)
        return clientInfo.isSuccessful()
    }
}