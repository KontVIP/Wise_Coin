package com.kontvip.wisecoin.presentation.navigation

import androidx.fragment.app.FragmentManager

interface Navigation {
    fun navigateTo(destination: Destination, fragmentManager: FragmentManager)

    class Default(private val containerId: Int) : Navigation {
        override fun navigateTo(destination: Destination, fragmentManager: FragmentManager) {
            destination.addTransaction(fragmentManager.beginTransaction(), containerId).commit()
        }
    }
}