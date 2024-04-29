package com.kontvip.wisecoin.data.cloud.mapper

import com.kontvip.wisecoin.domain.core.ServerResult
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException

interface ServerResultMapper {

    suspend fun <T> map(block: suspend () -> Response<T>): ServerResult<T>

    class Default : ServerResultMapper {

        override suspend fun <T> map(block: suspend () -> Response<T>): ServerResult<T> {
            try {
                val response = block.invoke()

                val code = response.code()
                if (code == 429) {
                    return ServerResult.Error.TooManyRequests()
                }
                if (response.code() in 400..499) {
                    return ServerResult.Error.UnknownToken()
                }
                return ServerResult.Success(response.body()!!)
            } catch (e: UnknownHostException) {
                return ServerResult.Error.NoInternetConnection()
            } catch (e: Exception) {
                return ServerResult.Error.UnknownError()
            }
        }
    }
}