package com.kontvip.wisecoin.presentation

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kontvip.wisecoin.core.DispatcherList
import com.kontvip.wisecoin.domain.CredentialsInteractor
import com.kontvip.wisecoin.presentation.navigation.Destination
import com.kontvip.wisecoin.presentation.navigation.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigation: Navigation,
    private val credentialsInteractor: CredentialsInteractor,
    private val dispatcherList: DispatcherList
) : ViewModel() {

    fun init(isFirstRun: Boolean, fragmentManager: FragmentManager) {
        if (isFirstRun) {
            viewModelScope.launch(dispatcherList.io()) {
                val destination = if (credentialsInteractor.shouldAuthorize()) {
                    Destination.AuthScreen
                } else {
                    Destination.PagerScreen
                }
                withContext(dispatcherList.ui()) {
                    navigation.navigateTo(destination, fragmentManager)
                }
            }
        }
    }

}