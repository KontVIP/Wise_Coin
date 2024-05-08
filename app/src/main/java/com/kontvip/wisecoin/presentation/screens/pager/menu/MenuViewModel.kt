package com.kontvip.wisecoin.presentation.screens.pager.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kontvip.wisecoin.core.DispatcherList
import com.kontvip.wisecoin.domain.CredentialsInteractor
import com.kontvip.wisecoin.domain.TransactionsInteractor
import com.kontvip.wisecoin.domain.model.ClientInfo
import com.kontvip.wisecoin.domain.model.Currency
import com.kontvip.wisecoin.presentation.core.NavigationCommunication
import com.kontvip.wisecoin.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val credentialsInteractor: CredentialsInteractor,
    private val navigationCommunication: NavigationCommunication,
    private val transactionsInteractor: TransactionsInteractor,
    private val dispatcherList: DispatcherList
) : ViewModel() {

    fun fetchClientInfo(onFetched: (ClientInfo) -> Unit) {
        viewModelScope.launch(dispatcherList.io()) {
            onFetched.invoke(credentialsInteractor.fetchCachedClientInfo())
        }
    }

    fun changeToken() {
        credentialsInteractor.clearMonobankToken()
        credentialsInteractor.saveIsSkippedMonobankAuth(false)
        navigateToAuthScreen()
    }

    fun navigateToAuthScreen() {
        credentialsInteractor.saveIsSkippedMonobankAuth(false)
        navigationCommunication.postValue(Destination.AuthManuallyScreen)
    }

    fun removeToken() {
        credentialsInteractor.clearMonobankToken()
        credentialsInteractor.saveIsSkippedMonobankAuth(true)
    }

    fun saveCurrency(currency: Currency) {
        transactionsInteractor.saveUserCurrency(currency)
    }

    fun getUserCurrency(): Currency {
        return transactionsInteractor.getUserCurrency()
    }

    fun isMonoTokenValid(): Boolean {
        return credentialsInteractor.isMonoTokenValid()
    }

}