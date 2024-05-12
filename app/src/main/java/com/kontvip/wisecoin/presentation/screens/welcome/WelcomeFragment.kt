package com.kontvip.wisecoin.presentation.screens.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kontvip.wisecoin.databinding.FragmentWelcomeBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.core.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    private val viewModel by viewModels<WelcomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.monobankButton.onClick {
            viewModel.navigateToLogin()
        }

        binding.skipTextView.onClick {
            viewModel.navigateToMainScreen()
        }
    }

}