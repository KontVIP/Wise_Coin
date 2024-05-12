package com.kontvip.wisecoin.presentation.core

import android.view.View

fun View.onClick(listener: (View) -> Unit) {
    setOnClickListener(listener)
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}