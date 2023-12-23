package com.kontvip.wisecoin.data.cloud

import com.kontvip.wisecoin.data.cloud.api.MonobankApi
import com.kontvip.wisecoin.data.cloud.mapper.ResponseClientInfoMapper
import com.kontvip.wisecoin.data.model.ClientInfo
import java.lang.Exception
import java.net.UnknownHostException

interface CloudSource {

    suspend fun fetchClientInfo(token: String): ClientInfo

    class Default(
        private val monobankApi: MonobankApi,
        private val responseClientInfoMapper: ResponseClientInfoMapper
    ) : CloudSource {
        override suspend fun fetchClientInfo(token: String): ClientInfo {
            return responseClientInfoMapper.map {
                monobankApi.fetchClientInfo(token)
            }
        }
    }

}