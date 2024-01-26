package com.kontvip.wisecoin.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.ActivityMainBinding
import com.kontvip.wisecoin.presentation.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.init(isFirstRun = savedInstanceState == null)

        val navigationManager = NavigationManager.Default(
            supportFragmentManager, R.id.fragmentContainer
        )
        viewModel.observeDestinations(this) { destination ->
            navigationManager.navigateTo(destination)
        }

        viewModel.observeSnackbars(this) { wiseCoinSnackbar ->
            wiseCoinSnackbar.display(binding.root)
        }
    }
}