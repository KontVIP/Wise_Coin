package com.kontvip.wisecoin.data

import com.kontvip.wisecoin.data.cache.CacheSource
import com.kontvip.wisecoin.data.cloud.CloudSource
import com.kontvip.wisecoin.data.model.ClientInfo
import com.kontvip.wisecoin.data.model.DefaultMonobankToken
import com.kontvip.wisecoin.domain.MonobankToken
import com.kontvip.wisecoin.domain.Repository

class DefaultRepository(
    private val cacheSource: CacheSource,
    private val cloudSource: CloudSource
) : Repository {
    override fun saveMonobankToken(token: String) {
        cacheSource.saveMonobankToken(token)
    }

    override fun getMonobankToken(): MonobankToken {
        return DefaultMonobankToken(cacheSource.getMonobankToken())
    }

    override suspend fun fetchCloudClientInfo(token: String): ClientInfo {
        val clientInfo = cloudSource.fetchClientInfo(token)
        if (clientInfo.isValid()) {
            cacheSource.saveClientInfo(clientInfo)
        } else {
            cacheSource.clearClientInfo()
        }
        return clientInfo
    }

    override suspend fun fetchCacheClientInfo(): ClientInfo {
        return cacheSource.fetchClientInfo()
    }
}