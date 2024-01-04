package com.kontvip.wisecoin.presentation.core.delegate

import android.view.View
import com.kontvip.wisecoin.presentation.core.CustomViewViewModel
import com.kontvip.wisecoin.presentation.core.CustomViewViewModelProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class CustomViewViewModelDelegate<T : CustomViewViewModel>(
    private val modelClass: Class<T>
) : ReadOnlyProperty<View, T> {
    override fun getValue(thisRef: View, property: KProperty<*>): T {
        return (thisRef.context as CustomViewViewModelProvider).provideViewModel(modelClass)
    }
}