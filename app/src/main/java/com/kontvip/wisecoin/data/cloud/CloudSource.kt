package com.kontvip.wisecoin.data.cloud

import com.kontvip.wisecoin.data.cloud.api.MonobankApi
import com.kontvip.wisecoin.data.cloud.firebase.WiseCoinFirebase
import com.kontvip.wisecoin.data.cloud.mapper.ServerResultMapper
import com.kontvip.wisecoin.data.core.IdRequest
import com.kontvip.wisecoin.data.model.ClientInfo
import com.kontvip.wisecoin.data.model.PaymentData
import com.kontvip.wisecoin.domain.core.ServerResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface CloudSource {

    suspend fun fetchClientInfo(token: String): ServerResult<ClientInfo>
    suspend fun fetchPayments(token: String, from: Long, to: Long): ServerResult<Array<PaymentData>>
    suspend fun shouldFetchDataFromFirebase(clientInfo: ClientInfo, lastUpdateTime: Long): Boolean

    class Default(
        private val monobankApi: MonobankApi,
        private val wiseCoinFirebase: WiseCoinFirebase,
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
            //todo: test values
            return serverResultMapper.map { monobankApi.fetchPaymentsData(token, 1697540431, 1700132431) }
        }

        override suspend fun shouldFetchDataFromFirebase(
            clientInfo: ClientInfo, lastUpdateTime: Long
        ): Boolean = suspendCoroutine { continuation ->
            clientInfo.onIdRequested(object : IdRequest {
                override fun onIdProvided(id: String) {
                    wiseCoinFirebase.lastUpdateTimeMillis(id, onSuccessListener = {
                        val timeMillis = it.getValue(Long::class.java) ?: 0
                        continuation.resume(timeMillis > lastUpdateTime)
                    }, onFailureListener = {
                        continuation.resume(false)
                    })
                }
            })
        }
    }

}