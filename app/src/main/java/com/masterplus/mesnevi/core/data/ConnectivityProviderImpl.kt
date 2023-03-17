package com.masterplus.mesnevi.core.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.masterplus.mesnevi.core.domain.ConnectivityProvider
import javax.inject.Inject

class ConnectivityProviderImpl @Inject constructor(
    context: Context
): ConnectivityProvider {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private fun hasConnection(capabilities: NetworkCapabilities): Boolean{
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    override fun hasConnection(): Boolean{
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return hasConnection(actNw)
    }

}