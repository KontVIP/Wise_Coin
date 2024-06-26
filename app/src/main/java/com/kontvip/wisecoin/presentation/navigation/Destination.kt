package com.kontvip.wisecoin.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.kontvip.wisecoin.presentation.model.CategoryItem
import com.kontvip.wisecoin.presentation.screens.auth.AuthAutoExtractionFragment
import com.kontvip.wisecoin.presentation.screens.auth.AuthManuallyFragment
import com.kontvip.wisecoin.presentation.screens.pager.PagerFragment
import com.kontvip.wisecoin.presentation.screens.auth.AuthPreloadingFragment
import com.kontvip.wisecoin.presentation.screens.add.AddTransactionFragment
import com.kontvip.wisecoin.presentation.screens.category.CategoryFragment
import com.kontvip.wisecoin.presentation.screens.welcome.WelcomeFragment

abstract class Destination(private val canNavigateBack: Boolean) {

    open fun addTransaction(transaction: FragmentTransaction, container: Int): FragmentTransaction {
        return if (canNavigateBack) {
            val fragment = fragment()
            transaction.addToBackStack(fragment.tag).replace(container, fragment)
        } else {
            transaction.replace(container, fragment())
        }
    }

    protected abstract fun fragment(): Fragment

    object WelcomeScreen : Destination(canNavigateBack = true) {
        override fun fragment(): Fragment = WelcomeFragment()
    }

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

    object AddTransactionScreen : Destination(canNavigateBack = true) {
        override fun fragment(): Fragment = AddTransactionFragment()
    }

    class CategoryScreen(private val categoryItem: CategoryItem) : Destination(canNavigateBack = true) {
        override fun fragment(): Fragment = CategoryFragment().apply { addCategoryItem(categoryItem) }
    }
}