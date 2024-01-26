package com.kontvip.wisecoin.presentation.screens.auth

import android.os.Bundle
import android.view.View

class AuthManuallyFragment : AuthFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginWithTokenButton.visibility = View.VISIBLE
        binding.loginWithTokenButton.setOnClickListener {
            extractTokenAndTryToLogin()
        }
    }

}