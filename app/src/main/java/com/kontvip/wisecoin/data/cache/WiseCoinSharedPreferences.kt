package com.kontvip.wisecoin.data.cache

import android.content.Context

interface WiseCoinSharedPreferences {

    fun getMonobankToken(): String
    fun getClientId(): String
    fun getClientName(): String
    fun getIsMonobankAuthSkipped(): Boolean
    fun saveIsSkippedMonobankAuth(isSkipped: Boolean)
    fun saveMonobankToken(token: String)
    fun saveClientId(id: String)
    fun saveClientName(name: String)


    class Default(context: Context) : WiseCoinSharedPreferences {

        companion object {
            private const val WISE_COIN_SHARED_PREFERENCES = "WISE_COIN_SHARED_PREFERENCES"
            private const val TOKEN_KEY = "TOKEN_KEY"
            private const val CLIENT_ID_KEY = "CLIENT_ID_KEY"
            private const val CLIENT_NAME_KEY = "CLIENT_NAME_KEY"
            private const val IS_MONOBANK_AUTH_SKIPPED_KEY = "IS_MONOBANK_AUTH_SKIPPED_KEY"
        }

        private val sharedPrefs =
            context.getSharedPreferences(WISE_COIN_SHARED_PREFERENCES, Context.MODE_PRIVATE)

        override fun getMonobankToken(): String {
            return sharedPrefs.getString(TOKEN_KEY, "") ?: ""
        }

        override fun getClientId(): String {
            return sharedPrefs.getString(CLIENT_ID_KEY, "") ?: ""
        }

        override fun getClientName(): String {
            return sharedPrefs.getString(CLIENT_NAME_KEY, "") ?: ""
        }

        override fun getIsMonobankAuthSkipped(): Boolean {
            return sharedPrefs.getBoolean(IS_MONOBANK_AUTH_SKIPPED_KEY, false)
        }

        override fun saveIsSkippedMonobankAuth(isSkipped: Boolean) {
            sharedPrefs.edit().putBoolean(IS_MONOBANK_AUTH_SKIPPED_KEY, isSkipped).apply()
        }

        override fun saveMonobankToken(token: String) {
            sharedPrefs.edit().putString(TOKEN_KEY, token).apply()
        }

        override fun saveClientId(id: String) {
            sharedPrefs.edit().putString(CLIENT_ID_KEY, id).apply()
        }

        override fun saveClientName(name: String) {
            sharedPrefs.edit().putString(CLIENT_NAME_KEY, name).apply()
        }
    }

}