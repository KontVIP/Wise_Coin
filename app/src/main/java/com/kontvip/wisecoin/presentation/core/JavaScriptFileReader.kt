package com.kontvip.wisecoin.presentation.core

import android.content.res.Resources
import androidx.annotation.RawRes
import com.kontvip.wisecoin.R
import java.util.Scanner

interface JavaScriptFileReader {

    fun javaScriptCodeFromRawId(@RawRes rawId: Int): String

    class Default(private val resources: Resources) : JavaScriptFileReader {

        override fun javaScriptCodeFromRawId(rawId: Int): String {
            val stream = resources.openRawResource(R.raw.reload_page_if_loader_is_shown)
            val scanner = Scanner(stream).useDelimiter("\\A")
            return scanner.next()
        }
    }

}