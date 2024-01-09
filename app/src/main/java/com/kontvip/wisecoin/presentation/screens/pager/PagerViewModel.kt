package com.kontvip.wisecoin.presentation.screens.pager

import androidx.lifecycle.ViewModel
import com.kontvip.wisecoin.domain.CredentialsInteractor
import com.kontvip.wisecoin.presentation.core.NavigationCommunication
import com.kontvip.wisecoin.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(
    private val navigationCommunication: NavigationCommunication,
    private val credentialsInteractor: CredentialsInteractor
): ViewModel() {

    fun signOut() {
        credentialsInteractor.clearMonobankToken()
        navigationCommunication.postValue(Destination.AuthScreen)
    }

}