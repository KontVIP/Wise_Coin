package com.kontvip.wisecoin.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import com.kontvip.wisecoin.presentation.core.NavigationCommunication
import com.kontvip.wisecoin.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val navigationCommunication: NavigationCommunication
) : ViewModel() {

    fun navigateToLogin() {
        navigationCommunication.postValue(Destination.AuthAutoExtractionScreen)
    }

    fun navigateToMainScreen() {
        navigationCommunication.postValue(Destination.PagerScreen)
    }

}