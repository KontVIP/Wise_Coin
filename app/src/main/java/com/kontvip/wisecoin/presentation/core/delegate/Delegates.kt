package com.kontvip.wisecoin.presentation.core.delegate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel


inline fun <reified T : ViewModel> View.fragmentViewModels(): Lazy<T> {
    return lazy {
        try {
            findFragment<Fragment>().viewModels<T>().value
        } catch (e: Exception) {
            throw IllegalStateException("ViewModel can be used only after its Fragment.onCreateView() method called.")
        }
    }
}
