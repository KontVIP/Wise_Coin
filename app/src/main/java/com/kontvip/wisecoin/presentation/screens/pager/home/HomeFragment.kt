package com.kontvip.wisecoin.presentation.screens.pager.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontvip.wisecoin.databinding.FragmentHomeBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.model.CategoryItem
import com.kontvip.wisecoin.presentation.model.TransactionItem
import com.kontvip.wisecoin.presentation.model.Transactions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoryAdapter(
            listOf(
                CategoryItem("0", "first item", "https://cdn-icons-png.freepik.com/512/5973/5973800.png",
                    Transactions().apply { add(TransactionItem("0", "transaction one", 5000L, "https://www.buildrestfoods.com/wp-content/uploads/2020/08/green-apply.jpg")) }
                )
            )
        )

        binding.categoriesRecyclerView.adapter = adapter
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

}