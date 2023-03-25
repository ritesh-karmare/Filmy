package rk.entertainment.common.utils

import android.content.Context
import android.net.ConnectivityManager

object ConnectionUtils {

    // Check whether Device is connected to internet or not
    @JvmStatic
    fun isNetworkAvailable(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected ?: false
    }

}