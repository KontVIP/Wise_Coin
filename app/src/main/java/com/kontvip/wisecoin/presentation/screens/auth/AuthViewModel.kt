package com.kontvip.wisecoin.presentation.screens.auth

import androidx.lifecycle.ViewModel
import com.kontvip.wisecoin.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun saveToken(token: String) {
        repository.saveMonobankToken(token)
    }
}