package com.kontvip.wisecoin.di

import com.kontvip.wisecoin.presentation.core.NavigationCommunication
import com.kontvip.wisecoin.presentation.core.SnackbarCommunication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PresentationSingletonModule {

    @Provides
    @Singleton
    fun provideNavigationCommunication(): NavigationCommunication = NavigationCommunication()

    @Provides
    @Singleton
    fun provideSnackbarCommunication(): SnackbarCommunication = SnackbarCommunication()
}