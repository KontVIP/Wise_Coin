package com.kontvip.wisecoin.presentation

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.kontvip.wisecoin.domain.CredentialsInteractor
import com.kontvip.wisecoin.presentation.navigation.Destination
import com.kontvip.wisecoin.presentation.navigation.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigation: Navigation,
    private val credentialsInteractor : CredentialsInteractor
) : ViewModel() {

    fun init(isFirstRun: Boolean, fragmentManager: FragmentManager) {
        if (isFirstRun) {
            val destination = if (credentialsInteractor.shouldAuthorize()) {
                Destination.AuthScreen
            } else {
                Destination.PagerScreen
            }
            navigation.navigateTo(destination, fragmentManager)
        }
    }

}