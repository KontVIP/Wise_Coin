package com.kontvip.wisecoin.core

import android.app.Application
import com.kontvip.wisecoin.presentation.core.CustomViewViewModel
import com.kontvip.wisecoin.presentation.core.CustomViewViewModelProvider
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class App : Application(), CustomViewViewModelProvider {

    private lateinit var customViewViewModelFactory: CustomViewViewModelProvider

    override fun onCreate() {
        super.onCreate()

        val factoryEntryPoint = EntryPointAccessors.fromApplication(
            this,
            CustomViewViewModelFactoryProviderEntryPoint::class.java
        )
        customViewViewModelFactory = factoryEntryPoint.customViewViewModelFactory()
    }

    override fun <T : CustomViewViewModel> provideViewModel(modelClass: Class<T>): T {
        return customViewViewModelFactory.provideViewModel(modelClass)
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface CustomViewViewModelFactoryProviderEntryPoint {
        fun customViewViewModelFactory(): CustomViewViewModelProvider
    }
}