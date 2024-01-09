package com.kontvip.wisecoin.data.model

import com.kontvip.wisecoin.domain.MonobankToken
import com.kontvip.wisecoin.domain.TokenServerValidator

data class DefaultMonobankToken(
    private val token: String
) : MonobankToken {
    override suspend fun isAcceptableForServer(tokenServerValidator: TokenServerValidator): Boolean {
        return tokenServerValidator.isValid(token)
    }

    override fun isValid(): Boolean {
        return token.isNotBlank() && token != "null"
    }

    override fun toString(): String = token

}