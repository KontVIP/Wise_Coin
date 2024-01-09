package com.kontvip.wisecoin.presentation.core

import android.content.res.Resources
import androidx.annotation.RawRes
import java.util.Scanner

interface JavaScriptFileReader {

    fun javaScriptCodeFromRawRes(@RawRes rawId: Int): String

    class Default(private val resources: Resources) : JavaScriptFileReader {

        override fun javaScriptCodeFromRawRes(rawId: Int): String {
            val stream = resources.openRawResource(rawId)
            val scanner = Scanner(stream).useDelimiter("\\A")
            return scanner.next()
        }
    }

}