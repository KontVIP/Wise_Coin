package com.kontvip.wisecoin.domain.model

import androidx.annotation.StringRes
import com.kontvip.wisecoin.domain.core.UiState
import java.io.Serializable

data class Currency(
    @StringRes private val currencyRes: Int,
    @StringRes private val signRes: Int
) : UiState<Currency.DisplayCurrency>, Serializable {

    override fun display(uiDisplay: DisplayCurrency) {
        uiDisplay.displayCurrency(currencyRes = currencyRes, signRes = signRes)
    }

    interface DisplayCurrency : UiState.UiDisplay {
        fun displayCurrency(@StringRes currencyRes: Int, @StringRes signRes: Int)
    }
}