package com.kontvip.wisecoin.presentation.core.delegates

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentLifecycleScope : ReadOnlyProperty<View, LifecycleCoroutineScope?> {
    private var fragment: Fragment? = null

    override operator fun getValue(
        thisRef: View,
        property: KProperty<*>
    ): LifecycleCoroutineScope? {
        if (fragment == null) {
            fragment = thisRef.findFragment()
        }
        return if (fragment?.isAdded == true) fragment?.lifecycleScope else null
    }
}