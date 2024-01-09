package com.kontvip.wisecoin.di

import com.kontvip.wisecoin.domain.CredentialsInteractor
import com.kontvip.wisecoin.domain.Repository
import com.kontvip.wisecoin.domain.TokenServerValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideCredentialsInteractor(
        repository: Repository,
        tokenServerValidator: TokenServerValidator
    ): CredentialsInteractor = CredentialsInteractor.Default(repository, tokenServerValidator)
}