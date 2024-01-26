package com.kontvip.wisecoin.data.cloud

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.kontvip.wisecoin.data.cloud.api.MonobankApi
import com.kontvip.wisecoin.data.cloud.firebase.WiseCoinFirebase
import com.kontvip.wisecoin.data.cloud.mapper.ResponseClientInfoMapper
import com.kontvip.wisecoin.data.core.IdProvide
import com.kontvip.wisecoin.data.core.IdRequest
import com.kontvip.wisecoin.data.model.ClientInfo

interface CloudSource {

    suspend fun fetchClientInfo(token: String): ClientInfo

    fun lastUpdateTimeMillis(
        clientInfo: ClientInfo,
        onSuccessListener: OnSuccessListener<DataSnapshot>,
        onFailureListener: OnFailureListener
    )

    class Default(
        private val monobankApi: MonobankApi,
        private val wiseCoinFirebase: WiseCoinFirebase,
        private val responseClientInfoMapper: ResponseClientInfoMapper
    ) : CloudSource {
        override suspend fun fetchClientInfo(token: String): ClientInfo {
            return responseClientInfoMapper.map {
                monobankApi.fetchClientInfo(token)
            }
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