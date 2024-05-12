package com.kontvip.wisecoin.presentation.screens.pager.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kontvip.wisecoin.core.DispatcherList
import com.kontvip.wisecoin.domain.model.TransactionPeriod
import com.kontvip.wisecoin.domain.TransactionsInteractor
import com.kontvip.wisecoin.domain.model.Currency
import com.kontvip.wisecoin.domain.model.PaymentDomain
import com.kontvip.wisecoin.presentation.core.NavigationCommunication
import com.kontvip.wisecoin.presentation.model.CategoryItem
import com.kontvip.wisecoin.presentation.model.PaymentUi
import com.kontvip.wisecoin.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionsInteractor: TransactionsInteractor,
    private val dispatcherList: DispatcherList,
    private val paymentMapper: PaymentDomain.Mapper<PaymentUi>,
    private val navigationCommunication: NavigationCommunication
) : ViewModel() {

    fun fetchCategories(period: TransactionPeriod, onFetched: (List<CategoryItem>) -> Unit) {
        viewModelScope.launch(dispatcherList.io()) {
            val payments = transactionsInteractor.fetchCachedPayments(period, paymentMapper)
            val categories = payments.groupBy { it.getCategory() }.map {
                CategoryItem(it.key, it.value)
            }
            withContext(dispatcherList.ui()) {
                onFetched.invoke(categories)
            }
        }
    }

    fun fetchExpenses(period: TransactionPeriod, onFetched: (List<CategoryItem>) -> Unit) {
        fetchCategories(period) {
            onFetched.invoke(it.filter { it.getTotalCost() < 0 })
        }
    }

    fun fetchIncomes(period: TransactionPeriod, onFetched: (List<CategoryItem>) -> Unit) {
        fetchCategories(period) {
            onFetched.invoke(it.filter { it.getTotalCost() >= 0 })
        }
    }

    fun navigateToAddTransactionScreen() {
        navigationCommunication.postValue(Destination.AddTransactionScreen)
    }

    fun navigateToCategoryScreen(categoryItem: CategoryItem) {
        navigationCommunication.postValue(Destination.CategoryScreen(categoryItem))
    }

    fun getUserCurrency(): Currency {
        return transactionsInteractor.getUserCurrency()
    }

}