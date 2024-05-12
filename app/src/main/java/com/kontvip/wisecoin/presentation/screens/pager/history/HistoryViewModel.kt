package com.kontvip.wisecoin.presentation.screens.pager.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kontvip.wisecoin.core.DispatcherList
import com.kontvip.wisecoin.domain.model.TransactionPeriod
import com.kontvip.wisecoin.domain.TransactionsInteractor
import com.kontvip.wisecoin.domain.model.PaymentDomain
import com.kontvip.wisecoin.presentation.model.PaymentUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transactionsInteractor: TransactionsInteractor,
    private val dispatcherList: DispatcherList,
    private val domainToUiPaymentMapper: PaymentDomain.Mapper<PaymentUi>
) : ViewModel() {

    fun fetchMonobankPayments(onSuccess: suspend (List<PaymentUi>) -> Unit) {
        viewModelScope.launch(dispatcherList.io()) {
            val payments = transactionsInteractor.fetchCachedPayments(TransactionPeriod.Year, domainToUiPaymentMapper)
            withContext(dispatcherList.ui()) {
                onSuccess.invoke(payments)
            }
        }
    }

    fun deleteTransaction(id: String) {
        viewModelScope.launch(dispatcherList.io()) {
            transactionsInteractor.deleteTransaction(id)
        }
    }

}