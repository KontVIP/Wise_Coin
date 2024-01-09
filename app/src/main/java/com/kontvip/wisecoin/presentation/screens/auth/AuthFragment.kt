package com.kontvip.wisecoin.presentation.screens.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kontvip.wisecoin.databinding.FragmentAuthBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    private val viewModel by viewModels<AuthViewModel>()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun extract() {
            binding.authWebView.extractToken { token ->
                if (token.isValid()) {
                    binding.tokenTextView.text = token.toString()
                    binding.tokenTextView.visibility = View.VISIBLE
                    binding.authWebView.visibility = View.GONE
                    binding.progressbar.visibility = View.VISIBLE
                    viewModel.tryToLoginWithToken(token)
                } else {
                    binding.tokenTextView.visibility = View.GONE
                    lifecycleScope.launch {
                        delay(200L)
                        extract()
                    }
                }
            }
        }

        extract()
    }

}