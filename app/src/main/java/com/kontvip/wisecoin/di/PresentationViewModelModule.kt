package com.kontvip.wisecoin.di

import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.presentation.navigation.Navigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class PresentationViewModelModule {

    @Provides
    fun provideNavigation(): Navigation = Navigation.Default(containerId = R.id.fragmentContainer)
}