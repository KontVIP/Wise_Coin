package com.kontvip.wisecoin.data.cache

import com.kontvip.wisecoin.data.model.ClientInfo

interface CacheSource {

    fun saveMonobankToken(token: String)
    fun getMonobankToken(): String

    fun saveClientInfo(clientInfo: ClientInfo)
    fun fetchClientInfo(): ClientInfo
    fun clearClientInfo()

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
            //TODO("Not yet implemented")
            return ClientInfo.Error.NoClientInfo()
        }

        override fun clearClientInfo() {
            //TODO("Not yet implemented")
        }
    }

}