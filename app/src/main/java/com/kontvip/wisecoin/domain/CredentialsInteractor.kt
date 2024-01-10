package com.kontvip.wisecoin.domain

interface CredentialsInteractor {
    suspend fun isSavedTokenValidOnServer(): Boolean
    fun isSavedTokenValid(): Boolean
    fun saveMonobankToken(token: String)
    fun clearMonobankToken()

    class Default(
        private val repository: Repository,
        private val tokenServerValidator: TokenServerValidator
    ) : CredentialsInteractor {
        override suspend fun isSavedTokenValidOnServer(): Boolean {
            val token = repository.getMonobankToken()
            return token.isAcceptableForServer(tokenServerValidator)
        }

        override fun isSavedTokenValid(): Boolean {
            val token = repository.getMonobankToken()
            return token.isValid()
        }

        override fun saveMonobankToken(token: String) {
            repository.saveMonobankToken(token)
        }

        override fun clearMonobankToken() {
            repository.saveMonobankToken("")
        }

    }
}