package com.kontvip.wisecoin.data

import android.content.Context
import androidx.annotation.RawRes
import androidx.annotation.StringRes

interface ResourceProvider {

    fun retrieveRawAsString(@RawRes rawRes: Int): String
    fun getString(@StringRes stringRes: Int): String

    class Default(private val context: Context): ResourceProvider {
        override fun retrieveRawAsString(rawRes: Int): String {
            return context.resources.openRawResource(rawRes).bufferedReader().use { it.readText() }
        }

        override fun getString(stringRes: Int): String {
            return context.getString(stringRes)
        }

    }

}