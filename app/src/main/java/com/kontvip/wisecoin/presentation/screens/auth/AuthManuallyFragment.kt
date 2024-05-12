package com.kontvip.wisecoin.presentation.screens.auth

import android.os.Bundle
import android.view.View
import com.kontvip.wisecoin.presentation.core.gone
import com.kontvip.wisecoin.presentation.core.onClick
import com.kontvip.wisecoin.presentation.core.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthManuallyFragment : AuthFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginWithTokenButton.visible()
        binding.loginWithTokenButton.onClick {
            binding.loginWithTokenButton.gone()
            extractTokenAndTryToLogin(onTokenInvalid = {
                binding.loginWithTokenButton.visible()
                viewModel.displayUnableToLoginWithCurrentTokenMessage()
            })
        }
    }

}