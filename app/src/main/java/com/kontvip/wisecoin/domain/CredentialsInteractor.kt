package com.kontvip.wisecoin.domain

import com.kontvip.wisecoin.domain.model.ClientInfo

interface CredentialsInteractor {
    suspend fun isSavedTokenValidOnServer(): Boolean
    fun shouldAuthorize(): Boolean
    fun isMonoTokenValid(): Boolean
    fun saveIsSkippedMonobankAuth(isSkipped: Boolean)
    fun saveMonobankToken(token: String)
    suspend fun fetchCachedClientInfo(): ClientInfo
    fun clearMonobankToken()

    class Default(
        private val repository: Repository,
        private val tokenServerValidator: TokenServerValidator
    ) : CredentialsInteractor {
        override suspend fun isSavedTokenValidOnServer(): Boolean {
            val token = repository.getMonobankToken()
            return token.isAcceptableForServer(tokenServerValidator)
        }

        override fun shouldAuthorize(): Boolean {
            val token = repository.getMonobankToken()
            return !repository.wasMonobankAuthSkipped() && !token.isValid()
        }

        override fun isMonoTokenValid(): Boolean {
            val token = repository.getMonobankToken()
            return token.isValid()
        }

        override fun saveIsSkippedMonobankAuth(isSkipped: Boolean) {
            repository.saveIsSkippedMonobankAuth(isSkipped)
        }

        override fun saveMonobankToken(token: String) {
            repository.saveMonobankToken(token)
        }

        override suspend fun fetchCachedClientInfo(): ClientInfo {
            return repository.fetchCachedClientInfo()
        }

        override fun clearMonobankToken() {
            repository.saveMonobankToken("")
        }

    }
}