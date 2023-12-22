package com.kontvip.wisecoin.data.cache

import android.content.Context
import android.content.SharedPreferences

interface WiseCoinSharedPreferences {

    fun saveMonobankToken(token: String)
    fun getMonobankToken(): String

    class Default(context: Context) : WiseCoinSharedPreferences {

        companion object {
            private const val WISE_COIN_SHARED_PREFERENCES = "WISE_COIN_SHARED_PREFERENCES"
            private const val TOKEN_KEY = "TOKEN_KEY"
        }

        private val sharedPrefs =
            context.getSharedPreferences(WISE_COIN_SHARED_PREFERENCES, Context.MODE_PRIVATE)

        override fun saveMonobankToken(token: String) {
            sharedPrefs.edit().putString(TOKEN_KEY, token).apply()
        }

        override fun getMonobankToken(): String {
            return sharedPrefs.getString(TOKEN_KEY, "") ?: ""
        }
    }

}