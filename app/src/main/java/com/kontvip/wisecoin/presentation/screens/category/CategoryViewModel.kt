package com.kontvip.wisecoin.presentation.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kontvip.wisecoin.core.DispatcherList
import com.kontvip.wisecoin.domain.TransactionsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val dispatcherList: DispatcherList,
    private val transactionsInteractor: TransactionsInteractor
) : ViewModel() {

    fun deleteTransaction(id: String) {
        viewModelScope.launch(dispatcherList.io()) {
            transactionsInteractor.deleteTransaction(id)
        }
    }
}