package com.kontvip.wisecoin.data.cache

import com.kontvip.wisecoin.data.model.ClientInfo

interface CacheSource {

    fun saveMonobankToken(token: String)
    fun getMonobankToken(): String

    fun saveClientInfo(clientInfo: ClientInfo)
    fun fetchClientInfo(): ClientInfo
    fun clearClientInfo()

    fun getLastUpdateTimeMillis(): Long

    class Default(
        private val wiseCoinSharedPreferences: WiseCoinSharedPreferences
    ) : CacheSource {

        override fun saveMonobankToken(token: String) {
            wiseCoinSharedPreferences.saveMonobankToken(token)
        }

        override fun getMonobankToken(): String {
            return wiseCoinSharedPreferences.getMonobankToken()
        }

        override fun saveClientInfo(clientInfo: ClientInfo) {
            //TODO("Not yet implemented")
        }

        override fun fetchClientInfo(): ClientInfo {
            TODO("Not yet implemented")
        }

        override fun clearClientInfo() {
            //TODO("Not yet implemented")
        }

        override fun getLastUpdateTimeMillis(): Long {
            TODO("Not yet implemented")
            //todo: add shared prefs
        }
    }

}