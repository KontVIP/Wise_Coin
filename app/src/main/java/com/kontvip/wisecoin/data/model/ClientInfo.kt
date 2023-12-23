package com.kontvip.wisecoin.data.model

interface ClientInfo {

    fun isValid(): Boolean

    data class DefaultClientInfo(
        private val name: String
    ) : ClientInfo {
        override fun isValid(): Boolean = true

    }

    abstract class Error : ClientInfo {

        override fun isValid(): Boolean = false

        class NoClientInfo() : Error()
        class UnknownToken : Error()
        class NoInternetConnection : Error()
        class UnknownError() : Error()

    }
}