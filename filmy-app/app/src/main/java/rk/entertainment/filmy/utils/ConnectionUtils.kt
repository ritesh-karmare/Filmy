package rk.entertainment.filmy.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import rk.entertainment.filmy.FilmyApplication

object ConnectionUtils {

    // Check whether Device is connected to internet or not
    @JvmStatic
    fun isNetworkAvailable(): Boolean {

        val connectivityManager = FilmyApplication.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo.isConnected
    }

}