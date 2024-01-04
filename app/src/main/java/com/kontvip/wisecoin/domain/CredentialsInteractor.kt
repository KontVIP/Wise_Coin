package com.kontvip.wisecoin.domain

interface CredentialsInteractor {
    suspend fun shouldAuthorize(): Boolean
    fun signOut()
    fun saveMonobankToken(token: String)

    class Default(
        private val repository: Repository,
        private val tokenValidator: TokenValidator
    ) : CredentialsInteractor {
        override suspend fun shouldAuthorize(): Boolean {
            val token = repository.getMonobankToken()
            return !token.isValid(tokenValidator)
        }

        override fun signOut() {
            repository.saveMonobankToken("")
        }

        override fun saveMonobankToken(token: String) {
            repository.saveMonobankToken(token)
        }
    }
}