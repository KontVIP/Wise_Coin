package com.kontvip.wisecoin.domain.model

import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.data.ResourceProvider
import java.lang.IllegalStateException

interface ServerResult<T> {

    fun isSuccessful(): Boolean = true
    fun extractData(): T
    fun error(resourceProvider: ResourceProvider): String
    fun canRepeatRequest(): Boolean

    class Success<T>(private val data: T) : ServerResult<T> {
        override fun extractData(): T = data
        override fun error(resourceProvider: ResourceProvider): String = ""
        override fun canRepeatRequest(): Boolean = false
    }

    abstract class Error<T> : ServerResult<T> {

        override fun isSuccessful(): Boolean = false
        override fun extractData(): Nothing {
            throw IllegalStateException("Data can't be extracted: ${this::class.java.simpleName}")
        }

        override fun canRepeatRequest(): Boolean = false

        class TooManyRequests<T> : Error<T>() {
            override fun error(resourceProvider: ResourceProvider): String =
                resourceProvider.getString(R.string.error_too_many_requests)

            override fun canRepeatRequest(): Boolean = true
        }

        class UnknownToken<T> : Error<T>() {
            override fun error(resourceProvider: ResourceProvider): String =
                resourceProvider.getString(R.string.error_unknown_token)
        }

        class NoInternetConnection<T> : Error<T>() {
            override fun error(resourceProvider: ResourceProvider): String =
                resourceProvider.getString(R.string.error_no_internet_connection)
        }
        class UnknownError<T> : Error<T>() {
            override fun error(resourceProvider: ResourceProvider): String =
                resourceProvider.getString(R.string.error_unknown)
        }

    }
}