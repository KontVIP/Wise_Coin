package com.kontvip.wisecoin.data.model

interface ClientInfo {

    fun isValid(): Boolean
    fun shouldRepeatRequest(): Boolean

    data class DefaultClientInfo(
        private val name: String
    ) : ClientInfo {
        override fun isValid(): Boolean = true
        override fun shouldRepeatRequest(): Boolean = false
    }

    abstract class Error : ClientInfo {

        override fun isValid(): Boolean = false
        override fun shouldRepeatRequest(): Boolean = false

        class TooManyRequests() : Error() {
            override fun shouldRepeatRequest(): Boolean = true
        }
        class NoClientInfo : Error()
        class UnknownToken : Error()
        class NoInternetConnection : Error()
        class UnknownError : Error()

    }
}