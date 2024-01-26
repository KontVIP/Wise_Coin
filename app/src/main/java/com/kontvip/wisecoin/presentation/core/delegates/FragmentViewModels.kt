package com.kontvip.wisecoin.presentation.core.delegates

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class FragmentViewModels<VM : ViewModel>(
    private val viewModelClass: KClass<VM>
) : ReadOnlyProperty<View, ViewModel> {
    private var cachedViewModel: VM? = null

    override operator fun getValue(thisRef: View, property: KProperty<*>): VM {
        if (cachedViewModel == null) {
            with(thisRef.findFragment<Fragment>()) {
                ViewModelProvider(
                    viewModelStore, defaultViewModelProviderFactory, defaultViewModelCreationExtras
                )[viewModelClass.java].also {
                    cachedViewModel = it
                }
            }
        }
        return cachedViewModel!!
    }
}