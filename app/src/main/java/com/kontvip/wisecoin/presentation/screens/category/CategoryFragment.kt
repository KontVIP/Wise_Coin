package com.kontvip.wisecoin.presentation.screens.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.databinding.FragmentCategoryBinding
import com.kontvip.wisecoin.presentation.core.OnRemoveTransaction
import com.kontvip.wisecoin.presentation.model.CategoryItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(), OnRemoveTransaction {

    private val viewModel by viewModels<CategoryViewModel>()
    private lateinit var category: CategoryItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category.display(object : CategoryItem.Display {
            override fun displayCategoryName(name: String) {
                binding.titleTextView.text = name
            }

            override fun displayCost(cost: String) = Unit
        })

        category.displayPayments {
            binding.paymentsRecyclerView.adapter = PaymentsAdapter(it.toMutableList(), this)
        }
        binding.paymentsRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        binding.paymentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    fun addCategoryItem(category: CategoryItem) {
        this.category = category

    }

    override fun onRemoveTransaction(id: String) {
        viewModel.deleteTransaction(id)
    }
}