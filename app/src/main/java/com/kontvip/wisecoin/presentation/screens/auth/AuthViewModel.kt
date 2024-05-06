package com.kontvip.wisecoin.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.core.DispatcherList
import com.kontvip.wisecoin.domain.CredentialsInteractor
import com.kontvip.wisecoin.domain.MonobankToken
import com.kontvip.wisecoin.domain.TransactionsInteractor
import com.kontvip.wisecoin.domain.model.PaymentDomain
import com.kontvip.wisecoin.presentation.core.NavigationCommunication
import com.kontvip.wisecoin.presentation.core.SnackbarCommunication
import com.kontvip.wisecoin.presentation.model.PaymentUi
import com.kontvip.wisecoin.presentation.navigation.Destination
import com.kontvip.wisecoin.presentation.snackbar.WiseCoinSnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val credentialsInteractor: CredentialsInteractor,
    private val dispatcherList: DispatcherList,
    private val navigationCommunication: NavigationCommunication,
    private val snackbarCommunication: SnackbarCommunication,
    private val transactionsInteractor: TransactionsInteractor,
    private val domainToUiPaymentMapper: PaymentDomain.Mapper<PaymentUi>
) : ViewModel() {

    fun tryToLoginWithToken(token: MonobankToken) {
        credentialsInteractor.saveMonobankToken(token.toString())
        viewModelScope.launch(dispatcherList.io()) {
            if (credentialsInteractor.isSavedTokenValidOnServer()) {
                transactionsInteractor.fetchPayments(
                    domainToUiPaymentMapper,
                    onSuccess = {
                        credentialsInteractor.saveIsSkippedMonobankAuth(false)
                        navigationCommunication.postValue(Destination.PagerScreen)
                    },
                    onError = {
                        snackbarCommunication.postValue(WiseCoinSnackbar.Error(messageRes = it))
                        navigationCommunication.postValue(Destination.PagerScreen)
                    }
                )
            } else {
                navigationCommunication.postValue(Destination.AuthManuallyScreen)
                displayUnableToLoginWithCurrentTokenMessage()
            }
        }
    }

    fun displayUnableToLoginWithCurrentTokenMessage() {
        snackbarCommunication.postValue(WiseCoinSnackbar.Error(R.string.authorization_error))
    }
}