package com.kontvip.wisecoin.presentation.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.PopupCurrencySelectionBinding
import com.kontvip.wisecoin.domain.model.Currency

class CurrencySelectionDialog(
    context: Context,
    private val onCurrencySelected: (Currency) -> Unit
) : AlertDialog(context) {

    init {
        val binding = PopupCurrencySelectionBinding.inflate(LayoutInflater.from(context))

        setTitle(R.string.select_currency)
        setView(binding.root)

        binding.buttonSelect.setOnClickListener {
            val currency = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.radioButtonDollar -> Currency(R.string.dollar, R.string.dollar_sign)
                R.id.radioButtonEuro -> Currency(R.string.euro, R.string.euro_sign)
                else -> Currency(R.string.hryvna, R.string.hryvna_sign)
            }
            onCurrencySelected(currency)
            dismiss()
        }
    }
}