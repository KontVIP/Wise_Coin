package com.kontvip.wisecoin.presentation.view

import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.data.model.DefaultMonobankToken
import com.kontvip.wisecoin.domain.core.MonobankToken
import com.kontvip.wisecoin.presentation.core.JavaScriptFileReader
import com.kontvip.wisecoin.presentation.core.SnackbarCommunication
import com.kontvip.wisecoin.presentation.snackbar.WiseCoinSnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthWebViewViewModel @Inject constructor(
    private val javaScriptFileReader: JavaScriptFileReader,
    private val snackbarCommunication: SnackbarCommunication
) : ViewModel() {

    fun javaScriptCodeFromRawRes(@RawRes rawRes: Int): String {
        return javaScriptFileReader.javaScriptCodeFromRawRes(rawRes)
    }

    fun processExtractedTokenValue(token: String?): MonobankToken {
        val processedToken = token?.replace("\"", "") ?: ""
        return DefaultMonobankToken(processedToken)
    }

    fun displayDocsClickError() {
        snackbarCommunication.postValue(WiseCoinSnackbar.Error(R.string.documentation_not_available))
    }
}