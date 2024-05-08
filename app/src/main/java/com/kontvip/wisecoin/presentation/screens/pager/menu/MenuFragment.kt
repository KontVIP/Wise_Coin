package com.kontvip.wisecoin.presentation.screens.pager.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.FragmentMenuBinding
import com.kontvip.wisecoin.domain.model.ClientInfo
import com.kontvip.wisecoin.domain.model.Currency
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.gone
import com.kontvip.wisecoin.presentation.onClick
import com.kontvip.wisecoin.presentation.view.CurrencySelectionDialog
import com.kontvip.wisecoin.presentation.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>(), Currency.DisplayCurrency {

    private val viewModel by viewModels<MenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserCurrency().display(this)
        viewModel.fetchClientInfo {
            it.display(object : ClientInfo.Display {
                override fun displayClientName(name: String) {
                    binding.clientNameTextView.text = name
                }
            })
        }

        if (viewModel.isMonoTokenValid()) {
            binding.activeTokenTextView.setText(R.string.monobank_token_is_active)
            binding.activeTokenImageView.setImageResource(R.drawable.ic_checkmark)
            binding.validTokenLayout.visible()
            binding.addTokenButton.gone()
        } else {
            onTokenNotValid()
        }

        binding.changeTokenButton.onClick {
            viewModel.changeToken()
            onTokenNotValid()
        }

        binding.removeTokenButton.onClick {
            viewModel.removeToken()
            onTokenNotValid()
        }

        binding.addTokenButton.onClick {
            viewModel.navigateToAuthScreen()
        }

        binding.changeCurrency.onClick {
            CurrencySelectionDialog(requireContext()) {
                it.display(this)
                viewModel.saveCurrency(it)
            }.show()
        }
    }

    private fun onTokenNotValid() {
        binding.activeTokenTextView.setText(R.string.monobank_token_is_not_active)
        binding.activeTokenImageView.setImageResource(R.drawable.ic_cross)
        binding.validTokenLayout.gone()
        binding.addTokenButton.visible()
    }

    override fun displayCurrency(currencyRes: Int, signRes: Int) {
        binding.currencyTextView.setText(currencyRes)
    }

}