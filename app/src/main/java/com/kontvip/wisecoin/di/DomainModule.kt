package com.kontvip.wisecoin.di

import com.kontvip.wisecoin.domain.CredentialsInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideCredentialsInteractor(): CredentialsInteractor = CredentialsInteractor.Default()
}