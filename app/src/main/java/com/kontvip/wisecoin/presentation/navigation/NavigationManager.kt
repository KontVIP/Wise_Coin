package com.kontvip.wisecoin.presentation.navigation

import androidx.fragment.app.FragmentManager

interface NavigationManager {
    fun navigateTo(destination: Destination)

    class Default(
        private val fragmentManager: FragmentManager,
        private val containerId: Int
    ) : NavigationManager {
        override fun navigateTo(destination: Destination) {
            destination.addTransaction(fragmentManager.beginTransaction(), containerId).commit()
        }
    }
}