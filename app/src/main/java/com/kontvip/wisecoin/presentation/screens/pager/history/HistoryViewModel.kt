package com.kontvip.wisecoin.presentation.screens.pager.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kontvip.wisecoin.core.DispatcherList
import com.kontvip.wisecoin.domain.TransactionsInteractor
import com.kontvip.wisecoin.domain.model.Payments
import com.kontvip.wisecoin.presentation.core.SnackbarCommunication
import com.kontvip.wisecoin.presentation.snackbar.WiseCoinSnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transactionsInteractor: TransactionsInteractor,
    private val dispatcherList: DispatcherList,
    private val snackbarCommunication: SnackbarCommunication
) : ViewModel() {

    fun fetchMonobankPayments(onSuccess: suspend (Payments) -> Unit) {
        viewModelScope.launch(dispatcherList.io()) {
            transactionsInteractor.fetchPaymentsData(
                onSuccess = {
                      withContext(dispatcherList.ui()) {
                          onSuccess.invoke(it)
                      }
                },
                onError = {
                    snackbarCommunication.postValue(WiseCoinSnackbar.Error(messageRes = it))
                }
            )
        }
    }

}