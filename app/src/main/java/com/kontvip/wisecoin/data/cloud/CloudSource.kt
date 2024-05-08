package com.kontvip.wisecoin.data.cloud

import com.kontvip.wisecoin.data.cloud.api.MonobankApi
import com.kontvip.wisecoin.data.cloud.mapper.ServerResultMapper
import com.kontvip.wisecoin.domain.model.ClientInfo
import com.kontvip.wisecoin.data.model.PaymentData
import com.kontvip.wisecoin.domain.core.ServerResult

interface CloudSource {

    suspend fun fetchClientInfo(token: String): ServerResult<ClientInfo>
    suspend fun fetchPayments(token: String, from: Long, to: Long): ServerResult<Array<PaymentData>>

    class Default(
        private val monobankApi: MonobankApi,
        private val serverResultMapper: ServerResultMapper
    ) : CloudSource {
        override suspend fun fetchClientInfo(token: String): ServerResult<ClientInfo> {
            return serverResultMapper.map {
                monobankApi.fetchClientInfo(token)
            }
        }

        override suspend fun fetchPayments(
            token: String, from: Long, to: Long
        ): ServerResult<Array<PaymentData>> {
            return serverResultMapper.map {
                monobankApi.fetchPaymentsData(token, from, to)
            }
        }
    }

}