package altline.foodspo.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService

class NetworkUtils(
    private val context: Context
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

