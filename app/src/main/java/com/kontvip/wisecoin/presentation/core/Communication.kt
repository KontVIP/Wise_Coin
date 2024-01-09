package com.kontvip.wisecoin.presentation.core

import androidx.lifecycle.MutableLiveData
import com.kontvip.wisecoin.domain.MonobankToken
import com.kontvip.wisecoin.presentation.navigation.Destination
import com.kontvip.wisecoin.presentation.snackbar.WiseCoinSnackbar

class NavigationCommunication(
    initialValue: Destination = Destination.AuthPreLoadingScreen
) : MutableLiveData<Destination>(initialValue)

class SnackbarCommunication : MutableLiveData<WiseCoinSnackbar>()

