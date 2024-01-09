package com.kontvip.wisecoin.data

import com.kontvip.wisecoin.domain.Repository
import com.kontvip.wisecoin.domain.TokenServerValidator

class DefaultTokenServerValidator(
    private val repository: Repository
) : TokenServerValidator {
    override suspend fun isValid(token: String): Boolean {
        val clientInfo = repository.fetchCloudClientInfo(token)
        return clientInfo.isValid()
    }
}