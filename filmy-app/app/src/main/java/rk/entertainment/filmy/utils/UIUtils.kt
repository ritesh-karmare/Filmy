package rk.entertainment.filmy.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

// Class containing functions releated to Android Componenets
// Eg ConvertDpToPx, check internet, display dialog
object UIUtils {

    // Convert the density pixels (dp) to Pixel (px)
    @JvmStatic
    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

    // Check whether Device is connected to internet or not
    @JvmStatic
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo.isConnected
    }

    // Function to display snackbar for messages, error, etc.
    @JvmStatic
    fun displayMessage(context: Context?, isSnackbar: Boolean, message: String?, view: View?,
                       durationLong: Boolean) {

        if (isSnackbar) {
            var duration = Snackbar.LENGTH_LONG
            if (durationLong) duration = Snackbar.LENGTH_SHORT
            Snackbar.make(view!!, message!!, duration).show()
        } else {
            var duration = Snackbar.LENGTH_LONG
            if (durationLong) duration = Snackbar.LENGTH_SHORT
            Toast.makeText(context, message, duration).show()
        }
    }
}