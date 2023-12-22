package com.kontvip.wisecoin.di

import com.kontvip.wisecoin.core.DispatcherList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideDispatchersList(): DispatcherList = DispatcherList.Default()

}