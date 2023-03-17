package com.masterplus.mesnevi.core.domain

interface ConnectivityProvider {

    fun hasConnection(): Boolean
}