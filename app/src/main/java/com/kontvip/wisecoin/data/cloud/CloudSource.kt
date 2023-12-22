package com.kontvip.wisecoin.data.cloud

import com.kontvip.wisecoin.data.cloud.api.MonobankApi
import com.kontvip.wisecoin.data.model.ClientInfo
import java.lang.Exception
import java.net.UnknownHostException

interface CloudSource {

    suspend fun fetchClientInfo(token: String): ClientInfo

    class Default(private val monobankApi: MonobankApi) : CloudSource {
        override suspend fun fetchClientInfo(token: String): ClientInfo {
            try {
                val response = monobankApi.fetchClientInfo(token)
                val code = response.code()
                if (code in 400..499) {
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