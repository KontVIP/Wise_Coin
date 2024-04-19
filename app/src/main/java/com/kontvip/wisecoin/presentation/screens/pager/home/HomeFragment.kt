package com.kontvip.wisecoin.presentation.screens.pager.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kontvip.wisecoin.databinding.FragmentHomeBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}