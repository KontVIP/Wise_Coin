package com.kontvip.wisecoin.presentation.view

import androidx.lifecycle.ViewModel
import com.kontvip.wisecoin.domain.Repository
import com.kontvip.wisecoin.presentation.core.CustomViewViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthWebViewViewModel @Inject constructor(
    private val repository: Repository
): CustomViewViewModel() {

    fun handle(): String {
        return repository.getMonobankToken().toString()
    }

}