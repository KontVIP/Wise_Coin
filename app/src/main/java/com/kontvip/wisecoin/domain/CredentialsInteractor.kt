package com.kontvip.wisecoin.domain

interface CredentialsInteractor {
    suspend fun isSavedTokenValidOnServer(): Boolean
    fun signOut()
    fun saveMonobankToken(token: String)

    class Default(
        private val repository: Repository,
        private val tokenServerValidator: TokenServerValidator
    ) : CredentialsInteractor {
        override suspend fun isSavedTokenValidOnServer(): Boolean {
            val token = repository.getMonobankToken()
            return token.isAcceptableForServer(tokenServerValidator)
        }

        override fun signOut() {
            repository.saveMonobankToken("")
        }

        override fun saveMonobankToken(token: String) {
            repository.saveMonobankToken(token)
        }
    }
}