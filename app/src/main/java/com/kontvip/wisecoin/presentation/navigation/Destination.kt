package com.kontvip.wisecoin.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.kontvip.wisecoin.presentation.screens.auth.AuthAutoExtractionFragment
import com.kontvip.wisecoin.presentation.screens.auth.AuthManuallyFragment
import com.kontvip.wisecoin.presentation.screens.pager.PagerFragment
import com.kontvip.wisecoin.presentation.screens.splash.AuthPreloadingFragment

abstract class Destination(private val canNavigateBack: Boolean) {

    open fun addTransaction(transaction: FragmentTransaction, container: Int): FragmentTransaction {
        return if (canNavigateBack) {
            transaction.add(container, fragment())
        } else {
            transaction.replace(container, fragment())
        }
    }

    protected abstract fun fragment(): Fragment

    object AuthPreLoadingScreen : Destination(canNavigateBack = false) {
        override fun fragment(): Fragment = AuthPreloadingFragment()
    }

    object AuthAutoExtractionScreen : Destination(canNavigateBack = false) {
        override fun fragment(): Fragment = AuthAutoExtractionFragment()
    }

    object AuthManuallyScreen : Destination(canNavigateBack = false) {
        override fun fragment(): Fragment = AuthManuallyFragment()
    }

    object PagerScreen : Destination(canNavigateBack = false) {
        override fun fragment(): Fragment = PagerFragment()
    }
}