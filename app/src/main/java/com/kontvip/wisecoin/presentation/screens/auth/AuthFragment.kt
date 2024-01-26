package com.kontvip.wisecoin.presentation.screens.auth

import android.view.View
import androidx.fragment.app.viewModels
import com.kontvip.wisecoin.databinding.FragmentAuthBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment

abstract class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    private val viewModel by viewModels<AuthViewModel>()

    protected fun extractTokenAndTryToLogin() {
        binding.authWebView.extractToken { token ->
            if (token.isValid()) {
                binding.authWebView.visibility = View.GONE
                binding.progressbar.visibility = View.VISIBLE
                viewModel.tryToLoginWithToken(token)
            }
        }
    }

}