package com.kontvip.wisecoin.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.kontvip.wisecoin.presentation.screens.auth.AuthFragment
import com.kontvip.wisecoin.presentation.screens.pager.PagerFragment

abstract class Destination(private val canNavigateBack: Boolean) {

    open fun addTransaction(transaction: FragmentTransaction, container: Int): FragmentTransaction {
        return if (canNavigateBack) {
            transaction.add(container, fragment())
        } else {
            transaction.replace(container, fragment())
        }
    }

    protected abstract fun fragment(): Fragment

    object AuthScreen : Destination(canNavigateBack = false) {
        override fun fragment(): Fragment = AuthFragment()
    }

    object PagerScreen : Destination(canNavigateBack = false) {
        override fun fragment(): Fragment = PagerFragment()
    }
}