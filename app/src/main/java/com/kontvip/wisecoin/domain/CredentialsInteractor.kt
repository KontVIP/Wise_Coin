package com.kontvip.wisecoin.domain

interface CredentialsInteractor {
    fun shouldAuthorize(): Boolean

    class Default : CredentialsInteractor {
        //todo
        override fun shouldAuthorize(): Boolean = true
    }
}