package com.kontvip.wisecoin.presentation.screens.pager.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.FragmentHomeBinding
import com.kontvip.wisecoin.domain.TransactionPeriod
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.model.CategoryItem
import com.kontvip.wisecoin.presentation.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnCategoryClick {

    private val viewModel by viewModels<HomeViewModel>()

    private var isExpensesSelected = true
    private var transactionPeriod: TransactionPeriod = TransactionPeriod.Month

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currency = viewModel.getUserCurrency()
        binding.pieChart.setCurrency(currency)
        val adapter = CategoryAdapter(emptyList(), isExpensesSelected, currency, this)
        binding.categoriesRecyclerView.adapter = adapter
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.categoriesRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        if (isExpensesSelected) fetchExpenses(adapter) else fetchIncomes(adapter)

        val transactionDisplay = object : TransactionPeriod.Display {
            override fun displayPeriodName(periodNameResource: Int) {
                binding.timePeriodTextView.setText(periodNameResource)
            }
        }
        binding.biggerPeriodImageView.onClick {
            transactionPeriod = transactionPeriod.next()
            transactionPeriod.display(transactionDisplay)
            if (isExpensesSelected) fetchExpenses(adapter) else fetchIncomes(adapter)
        }
        binding.smallerPeriodImageView.onClick {
            transactionPeriod = transactionPeriod.previous()
            transactionPeriod.display(transactionDisplay)
            if (isExpensesSelected) fetchExpenses(adapter) else fetchIncomes(adapter)
        }

        binding.expensesButton.onClick {
            if (!isExpensesSelected) {
                isExpensesSelected = true
                binding.expensesButton.setBackgroundResource(R.drawable.transaction_type_selected_bg)
                binding.incomeButton.setBackgroundResource(R.drawable.black_border)
                binding.expensesButton.setTextColor(Color.BLACK)
                binding.incomeButton.setTextColor(Color.WHITE)
                fetchExpenses(adapter)
            }
        }

        binding.incomeButton.onClick {
            if (isExpensesSelected) {
                isExpensesSelected = false
                binding.incomeButton.setBackgroundResource(R.drawable.transaction_type_selected_bg)
                binding.expensesButton.setBackgroundResource(R.drawable.black_border)
                binding.expensesButton.setTextColor(Color.WHITE)
                binding.incomeButton.setTextColor(Color.BLACK)
                fetchIncomes(adapter)
            }
        }

        binding.addTransactionFab.onClick {
            viewModel.navigateToAddTransactionScreen()
        }
    }

    private fun fetchExpenses(adapter: CategoryAdapter) {
        viewModel.fetchExpenses(transactionPeriod) {
            viewModel
            binding.pieChart.addCategoriesChartData(it, isExpensesSelected)
            adapter.changeItems(it, isExpensesSelected)
        }
    }

    private fun fetchIncomes(adapter: CategoryAdapter) {
        viewModel.fetchIncomes(transactionPeriod) {
            binding.pieChart.addCategoriesChartData(it, isExpensesSelected)
            adapter.changeItems(it, isExpensesSelected)
        }
    }

    override fun onClick(categoryItem: CategoryItem) {
        viewModel.navigateToCategoryScreen(categoryItem)
    }

}

interface OnCategoryClick {
    fun onClick(categoryItem: CategoryItem)
}