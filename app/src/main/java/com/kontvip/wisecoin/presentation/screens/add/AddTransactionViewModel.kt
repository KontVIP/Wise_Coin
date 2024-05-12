package com.kontvip.wisecoin.presentation.screens.add

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kontvip.wisecoin.core.DispatcherList
import com.kontvip.wisecoin.domain.model.TransactionPeriod
import com.kontvip.wisecoin.domain.TransactionsInteractor
import com.kontvip.wisecoin.domain.model.PaymentDomain
import com.kontvip.wisecoin.presentation.core.SnackbarCommunication
import com.kontvip.wisecoin.presentation.model.PaymentUi
import com.kontvip.wisecoin.presentation.snackbar.WiseCoinSnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionsInteractor: TransactionsInteractor,
    private val dispatcherList: DispatcherList,
    private val paymentMapper: PaymentDomain.Mapper<PaymentUi>,
    private val snackbarCommunication: SnackbarCommunication
) : ViewModel() {

    fun saveTransaction(time: Long, description: String, category: String, amount: Double, image: String) {
        val payment = PaymentDomain(
            Random(System.currentTimeMillis()).nextInt(0, Int.MAX_VALUE).toString(),
            time, description, category, amount, image
        )
        viewModelScope.launch(dispatcherList.io()) {
            transactionsInteractor.savePayment(payment)
        }
    }

    fun showError(@StringRes messageRes: Int) {
        snackbarCommunication.postValue(WiseCoinSnackbar.Error(messageRes))
    }

    fun fetchAllCategories(onFetched: (List<String>) -> Unit) {
        viewModelScope.launch(dispatcherList.io()) {
            val payments = transactionsInteractor.fetchCachedPayments(TransactionPeriod.Year, paymentMapper)

            val uniqueCategoriesSet = payments.map { it.getCategory() }.distinct()
            withContext(dispatcherList.ui()) {
                onFetched.invoke(uniqueCategoriesSet)
            }
        }
    }

}