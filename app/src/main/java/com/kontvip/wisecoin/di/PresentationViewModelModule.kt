package com.kontvip.wisecoin.di

import android.content.Context
import com.kontvip.wisecoin.domain.model.PaymentDomain
import com.kontvip.wisecoin.presentation.core.DomainToUiPaymentMapper
import com.kontvip.wisecoin.presentation.core.JavaScriptFileReader
import com.kontvip.wisecoin.presentation.model.PaymentUi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class PresentationViewModelModule {

    @Provides
    fun provideDomainToUiPaymentMapper(): PaymentDomain.Mapper<PaymentUi> = DomainToUiPaymentMapper()

    @Provides
    fun provideCredentialsInteractor(@ApplicationContext context: Context): JavaScriptFileReader =
        JavaScriptFileReader.Default(context.resources)
}