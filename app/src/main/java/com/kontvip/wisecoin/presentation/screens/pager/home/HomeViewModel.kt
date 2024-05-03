package com.kontvip.wisecoin.presentation.screens.pager.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kontvip.wisecoin.core.DispatcherList
import com.kontvip.wisecoin.domain.TransactionsInteractor
import com.kontvip.wisecoin.domain.model.PaymentDomain
import com.kontvip.wisecoin.presentation.model.CategoryItem
import com.kontvip.wisecoin.presentation.model.PaymentUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionsInteractor: TransactionsInteractor,
    private val dispatcherList: DispatcherList,
    private val paymentMapper: PaymentDomain.Mapper<PaymentUi>
) : ViewModel() {

    fun fetchCategories(onFetched: (List<CategoryItem>) -> Unit) {
        viewModelScope.launch(dispatcherList.io()) {
            val payments = transactionsInteractor.fetchCachedPayments(paymentMapper)
            val categories = payments.groupBy { it.getCategory() }.map {
                CategoryItem(it.key, it.value)
            }
            withContext(dispatcherList.ui()) {
                onFetched.invoke(categories)
            }
        }
    }

    fun fetchExpenses(onFetched: (List<CategoryItem>) -> Unit) {
        fetchCategories {
            onFetched.invoke(it.filter { it.getTotalCost() < 0 })
        }
    }

    fun fetchIncomes(onFetched: (List<CategoryItem>) -> Unit) {
        fetchCategories {
            onFetched.invoke(it.filter { it.getTotalCost() >= 0 })
        }
    }

}