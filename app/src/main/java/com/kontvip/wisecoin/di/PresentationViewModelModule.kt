package com.kontvip.wisecoin.di

import android.content.Context
import com.kontvip.wisecoin.presentation.core.JavaScriptFileReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class PresentationViewModelModule {


    @Provides
    fun provideCredentialsInteractor(@ApplicationContext context: Context): JavaScriptFileReader =
        JavaScriptFileReader.Default(context.resources)
}