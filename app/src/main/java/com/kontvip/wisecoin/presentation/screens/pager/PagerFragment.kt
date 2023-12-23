package com.kontvip.wisecoin.presentation.screens.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kontvip.wisecoin.databinding.FragmentPagerBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagerFragment : BaseFragment<FragmentPagerBinding>() {

    private val viewModel by viewModels<PagerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signOutButton.setOnClickListener {
            viewModel.signOut()
        }

    }
}