package com.kontvip.wisecoin.data

import android.content.Context
import androidx.annotation.RawRes

interface ResourceProvider {

    fun retrieveRawAsString(@RawRes rawRes: Int): String

    class Default(private val context: Context): ResourceProvider {
        override fun retrieveRawAsString(rawRes: Int): String {
            return context.resources.openRawResource(rawRes).bufferedReader().use { it.readText() }
        }

    }

}