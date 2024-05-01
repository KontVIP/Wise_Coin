package com.kontvip.wisecoin.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherList {

    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher

    class Default : DispatcherList {
        override fun io() = Dispatchers.IO

        override fun ui() = Dispatchers.Main
    }

}