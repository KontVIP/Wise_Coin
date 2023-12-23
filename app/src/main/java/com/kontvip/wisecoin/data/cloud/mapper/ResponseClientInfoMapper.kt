package com.kontvip.wisecoin.data.cloud.mapper

import com.kontvip.wisecoin.data.model.ClientInfo
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException

interface ResponseClientInfoMapper {

    suspend fun <T : ClientInfo> map(block: suspend () -> Response<T>): ClientInfo

    class Default : ResponseClientInfoMapper {
        override suspend fun <T : ClientInfo> map(block: suspend () -> Response<T>): ClientInfo {
            try {
                val response = block.invoke()
                if (response.code() in 400..499) {
                    return ClientInfo.Error.UnknownToken()
                }
                return response.body()!!
            } catch (e: UnknownHostException) {
                return ClientInfo.Error.NoInternetConnection()
            } catch (e: Exception) {
                return ClientInfo.Error.UnknownError()
            }
        }
    }
}