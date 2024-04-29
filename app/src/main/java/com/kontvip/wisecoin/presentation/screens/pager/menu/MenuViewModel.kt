package com.kontvip.wisecoin.presentation.screens.pager.menu

import androidx.lifecycle.ViewModel
import com.kontvip.wisecoin.domain.CredentialsInteractor
import com.kontvip.wisecoin.presentation.core.NavigationCommunication
import com.kontvip.wisecoin.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val credentialsInteractor: CredentialsInteractor,
    private val navigationCommunication: NavigationCommunication
) : ViewModel() {

    fun signOut() {
        credentialsInteractor.clearMonobankToken()
        navigationCommunication.postValue(Destination.AuthManuallyScreen)
    }

}