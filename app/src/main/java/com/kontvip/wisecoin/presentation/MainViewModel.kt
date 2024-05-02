package com.kontvip.wisecoin.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kontvip.wisecoin.core.DispatcherList
import com.kontvip.wisecoin.domain.CredentialsInteractor
import com.kontvip.wisecoin.domain.TransactionsInteractor
import com.kontvip.wisecoin.presentation.core.NavigationCommunication
import com.kontvip.wisecoin.presentation.core.SnackbarCommunication
import com.kontvip.wisecoin.presentation.navigation.Destination
import com.kontvip.wisecoin.presentation.snackbar.WiseCoinSnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigationCommunication: NavigationCommunication,
    private val credentialsInteractor: CredentialsInteractor,
    private val dispatcherList: DispatcherList,
    private val snackbarCommunication: SnackbarCommunication,
    private val transactionsInteractor: TransactionsInteractor
) : ViewModel() {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            viewModelScope.launch(dispatcherList.io()) {
                if (credentialsInteractor.isSavedTokenValid()) {
                    transactionsInteractor.updatePaymentsFromCloud {
                        navigationCommunication.postValue(Destination.PagerScreen)
                    }
                } else {
                    navigationCommunication.postValue(Destination.AuthAutoExtractionScreen)
                }
            }
        }
    }

    fun observeDestinations(lifecycleOwner: LifecycleOwner, observer: Observer<Destination>) {
        navigationCommunication.observe(lifecycleOwner, observer)
    }

    fun observeSnackbars(lifecycleOwner: LifecycleOwner, observer: Observer<WiseCoinSnackbar>) {
        snackbarCommunication.observe(lifecycleOwner, observer)
    }
}