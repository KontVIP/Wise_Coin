package com.kontvip.wisecoin.data

import com.kontvip.wisecoin.domain.Repository
import com.kontvip.wisecoin.domain.TokenValidator

class DefaultTokenValidator(
    private val repository: Repository
) : TokenValidator {
    override suspend fun isValid(token: String): Boolean {
        val clientInfo = repository.fetchCloudClientInfo(token)
        return clientInfo.isValid()
    }
}