package about.cats.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

sealed class NetworkStatus {
    companion object {

        fun getNetworkStatus(context: Context): NetworkStatus {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
                if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                    return Avaliable()
            }
            return Unavaliable()
        }
    }
}

class Avaliable: NetworkStatus()
class Unavaliable : NetworkStatus()
