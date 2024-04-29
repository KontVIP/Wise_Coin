package com.kontvip.wisecoin.data.cloud

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.kontvip.wisecoin.data.cloud.api.MonobankApi
import com.kontvip.wisecoin.data.cloud.firebase.WiseCoinFirebase
import com.kontvip.wisecoin.data.cloud.mapper.ServerResultMapper
import com.kontvip.wisecoin.data.core.IdRequest
import com.kontvip.wisecoin.data.model.ClientInfo
import com.kontvip.wisecoin.domain.core.ServerResult
import com.kontvip.wisecoin.domain.model.Payments

interface CloudSource {

    suspend fun fetchClientInfo(token: String): ServerResult<ClientInfo>
    suspend fun fetchPayments(token: String, from: Long, to: Long): ServerResult<Payments>

    fun lastUpdateTimeMillis(
        clientInfo: ClientInfo,
        onSuccessListener: OnSuccessListener<DataSnapshot>,
        onFailureListener: OnFailureListener
    )

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
        ): ServerResult<Payments> {
            return serverResultMapper.map { monobankApi.fetchPaymentsData(token, from, to) }
        }

        override fun lastUpdateTimeMillis(
            clientInfo: ClientInfo,
            onSuccessListener: OnSuccessListener<DataSnapshot>,
            onFailureListener: OnFailureListener
        ) {
            clientInfo.onIdRequested(object : IdRequest {
                override fun onIdProvided(id: String) {
                    wiseCoinFirebase.lastUpdateTimeMillis(id, onSuccessListener, onFailureListener)
                }
            })
        }
    }

}