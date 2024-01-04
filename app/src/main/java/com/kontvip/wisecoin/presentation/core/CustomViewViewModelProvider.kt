package com.kontvip.wisecoin.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kontvip.wisecoin.domain.Repository
import com.kontvip.wisecoin.presentation.view.AuthWebViewViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface CustomViewViewModelProvider {

    fun <T : CustomViewViewModel> provideViewModel(modelClass: Class<T>): T

    @ViewModelScoped
    class Default @Inject constructor(
        private val repository: Repository
    ) : ViewModelProvider.Factory, CustomViewViewModelProvider {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            when {
                modelClass.isAssignableFrom(AuthWebViewViewModel::class.java) -> {
                    return AuthWebViewViewModel(repository) as T
                }
                else -> {
                    throw IllegalArgumentException("Unknown or not custom view ViewModel class")
                }
            }
        }

        override fun <T : CustomViewViewModel> provideViewModel(modelClass: Class<T>): T {
            return create(modelClass)
        }
    }

}