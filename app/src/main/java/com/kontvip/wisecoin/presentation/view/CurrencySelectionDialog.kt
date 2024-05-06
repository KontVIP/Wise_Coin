package com.kontvip.wisecoin.presentation.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.RadioButton
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.PopupCurrencySelectionBinding

class CurrencySelectionDialog(
    context: Context,
    private val onCurrencySelected: (String) -> Unit
) : AlertDialog(context) {

    init {
        val binding = PopupCurrencySelectionBinding.inflate(LayoutInflater.from(context))

        setTitle(R.string.select_currency)
        setView(binding.root)

        binding.buttonSelect.setOnClickListener {
            val checkedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            if (checkedRadioButtonId != -1) {
                val selectedRadioButton =
                    binding.root.findViewById<RadioButton>(checkedRadioButtonId)
                val selectedCurrency = selectedRadioButton.text.toString()
                onCurrencySelected(selectedCurrency)
                dismiss()
            }
        }
    }
}