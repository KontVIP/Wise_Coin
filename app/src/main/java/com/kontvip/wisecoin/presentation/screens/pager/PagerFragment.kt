package com.kontvip.wisecoin.presentation.screens.pager

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.navigation.NavigationBarView
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.FragmentPagerBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.screens.pager.analytics.AnalyticsFragment
import com.kontvip.wisecoin.presentation.screens.pager.history.HistoryFragment
import com.kontvip.wisecoin.presentation.screens.pager.home.HomeFragment
import com.kontvip.wisecoin.presentation.screens.pager.menu.MenuFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagerFragment : BaseFragment<FragmentPagerBinding>(), NavigationBarView.OnItemSelectedListener {

    private val viewModel by viewModels<PagerViewModel>()

    private var isFragmentAdded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isFragmentAdded) {
            switchFragment(HomeFragment())
            isFragmentAdded = true
        }

        binding.bottomNavigation.setOnItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment = when (item.itemId) {
            R.id.navigationHome -> HomeFragment()
            R.id.navigationHistory -> HistoryFragment()
            R.id.navigationAnalytics -> AnalyticsFragment()
            R.id.navigationMenu -> MenuFragment()
            else -> return false
        }
        switchFragment(fragment)
        return true
    }

    private fun switchFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.pagerContainer, fragment)
            .addToBackStack(fragment.tag).commit()
    }

}