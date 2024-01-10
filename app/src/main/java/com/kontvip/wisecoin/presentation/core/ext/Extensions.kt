package com.kontvip.wisecoin.presentation.core.ext

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope


inline fun <reified T : ViewModel> View.fragmentViewModels(): Lazy<T> {
    return lazy {
        try {
            findFragment<Fragment>().viewModels<T>().value
        } catch (e: Exception) {
            throw IllegalStateException("ViewModel can be used only after its Fragment.onCreateView() method called.")
        }
    }
}

fun View.fragmentLifecycleScope(): LifecycleCoroutineScope {
    try {
    return findFragment<Fragment>().lifecycleScope
    } catch (e: Exception) {
        throw IllegalStateException("Fragment LifecycleScope can be used only after onCreateView() method called.")
    }
}
