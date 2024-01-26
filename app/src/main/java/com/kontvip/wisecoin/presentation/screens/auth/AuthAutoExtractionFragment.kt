package com.kontvip.wisecoin.presentation.screens.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthAutoExtractionFragment : AuthFragment() {

    companion object {
        private const val TOKEN_EXTRACTION_DELAY = 200L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun processExtraction() {
            lifecycleScope.launch(Dispatchers.Main) {
                delay(TOKEN_EXTRACTION_DELAY)
                if (isAdded) {
                    extractTokenAndTryToLogin(onTokenInvalid = {
                        processExtraction()
                    })
                }
            }
        }

        processExtraction()
    }

}