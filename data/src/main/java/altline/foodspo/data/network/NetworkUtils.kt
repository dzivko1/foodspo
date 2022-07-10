package altline.foodspo.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService<ConnectivityManager>()!!
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        
        return capabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    || hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        } ?: false
    }
}

