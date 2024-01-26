package com.kontvip.wisecoin.data.core

interface IdProvide {
    fun onIdRequested(idRequest: IdRequest)
}

interface IdRequest {
    fun onIdProvided(id: String)
}