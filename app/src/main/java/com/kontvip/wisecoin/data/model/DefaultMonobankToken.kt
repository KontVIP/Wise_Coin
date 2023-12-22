package com.kontvip.wisecoin.data.model

import com.kontvip.wisecoin.domain.MonobankToken
import com.kontvip.wisecoin.domain.TokenValidator

data class DefaultMonobankToken(
    private val token: String
) : MonobankToken {
    override suspend fun isValid(tokenValidator: TokenValidator): Boolean {
        return tokenValidator.isValid(token)
    }
}