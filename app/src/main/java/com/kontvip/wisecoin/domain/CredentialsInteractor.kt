package com.kontvip.wisecoin.domain

interface CredentialsInteractor {
    suspend fun shouldAuthorize(): Boolean

    class Default(
        private val repository: Repository,
        private val tokenValidator: TokenValidator
    ) : CredentialsInteractor {
        override suspend fun shouldAuthorize(): Boolean {
            val token = repository.getMonobankToken()
            return !token.isValid(tokenValidator)
        }
    }
}