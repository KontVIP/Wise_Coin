package com.kontvip.wisecoin.presentation.screens.pager.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontvip.wisecoin.databinding.FragmentHistoryBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.core.OnRemoveTransaction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(), OnRemoveTransaction {

    private val viewModel by viewModels<HistoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMonobankPayments {
            val adapter = HistoryAdapter(it.toMutableList(), this)
            binding.historyRecyclerView.adapter = adapter
        }

        binding.historyRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onRemoveTransaction(id: String) {
        viewModel.deleteTransaction(id)
    }

}