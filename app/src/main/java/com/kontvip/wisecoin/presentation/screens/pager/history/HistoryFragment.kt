package com.kontvip.wisecoin.presentation.screens.pager.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontvip.wisecoin.databinding.FragmentHistoryBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.model.CategoryItem
import com.kontvip.wisecoin.presentation.model.TransactionItem
import com.kontvip.wisecoin.presentation.model.Transactions
import com.kontvip.wisecoin.presentation.screens.pager.home.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private val viewModel by viewModels<HistoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMonobankPayments {
            val adapter = HistoryAdapter(it)
            binding.historyRecyclerView.adapter = adapter
        }

        binding.historyRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

}