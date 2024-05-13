package com.kontvip.wisecoin.presentation.snackbar

import android.graphics.Color
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

interface WiseCoinSnackbar {

    fun display(view: View)

    class Error(@StringRes private val messageRes: Int) : WiseCoinSnackbar {
        override fun display(view: View) {
            val snackbar = Snackbar.make(view, messageRes, Snackbar.LENGTH_SHORT)
            snackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.holo_red_dark))
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

}