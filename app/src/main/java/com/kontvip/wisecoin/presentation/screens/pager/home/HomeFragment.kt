package com.kontvip.wisecoin.presentation.screens.pager.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.FragmentHomeBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    private var isExpensesSelected = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoryAdapter(emptyList(), isExpensesSelected)
        binding.categoriesRecyclerView.adapter = adapter
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.categoriesRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        viewModel.fetchExpenses {
            binding.pieChart.addCategoriesChartData(it, isExpensesSelected)
            adapter.changeItems(it, isExpensesSelected)
        }

        binding.expensesTextView.onClick {
            if (!isExpensesSelected) {
                isExpensesSelected = true
                binding.expensesTextView.setBackgroundResource(R.drawable.transanction_type_selected_bg)
                binding.incomeTextView.setBackgroundResource(R.drawable.black_border)
                viewModel.fetchExpenses {
                    binding.pieChart.addCategoriesChartData(it, isExpensesSelected)
                    adapter.changeItems(it, isExpensesSelected)
                }
            }
        }

        binding.incomeTextView.onClick {
            if (isExpensesSelected) {
                isExpensesSelected = false
                binding.incomeTextView.setBackgroundResource(R.drawable.transanction_type_selected_bg)
                binding.expensesTextView.setBackgroundResource(R.drawable.black_border)
                viewModel.fetchIncomes {
                    binding.pieChart.addCategoriesChartData(it, isExpensesSelected)
                    adapter.changeItems(it, isExpensesSelected)
                }
            }
        }
    }

}