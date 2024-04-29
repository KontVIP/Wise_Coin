package com.kontvip.wisecoin.presentation.screens.auth

import android.os.Bundle
import android.view.View
import com.kontvip.wisecoin.presentation.gone
import com.kontvip.wisecoin.presentation.onClick
import com.kontvip.wisecoin.presentation.visible
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