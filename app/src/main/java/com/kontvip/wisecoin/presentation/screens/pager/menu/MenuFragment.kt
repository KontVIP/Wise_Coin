package com.kontvip.wisecoin.presentation.screens.pager.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kontvip.wisecoin.databinding.FragmentMenuBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    private val viewModel by viewModels<MenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signOutButton.onClick {
            viewModel.signOut()

        }
    }

}