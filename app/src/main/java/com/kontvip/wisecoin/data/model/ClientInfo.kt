package com.kontvip.wisecoin.data.model

import com.kontvip.wisecoin.data.core.IdProvide
import com.kontvip.wisecoin.data.core.IdRequest

interface ClientInfo : IdProvide {

    fun isValid(): Boolean
    fun canRepeatRequest(): Boolean

    data class Default(
        private val clientId: String,
        private val name: String,
    ) : ClientInfo {
        override fun isValid(): Boolean = true
        override fun canRepeatRequest(): Boolean = false
        override fun onIdRequested(idRequest: IdRequest) {
            idRequest.onIdProvided(clientId)
        }
    }

    abstract class Error : ClientInfo {

        override fun isValid(): Boolean = false
        override fun canRepeatRequest(): Boolean = false
        override fun onIdRequested(idRequest: IdRequest) {
            idRequest.onIdProvided("")
        }

        class TooManyRequests() : Error() {
            override fun canRepeatRequest(): Boolean = true
        }
        class NoClientInfo : Error()
        class UnknownToken : Error()
        class NoInternetConnection : Error()
        class UnknownError : Error()

    }
}