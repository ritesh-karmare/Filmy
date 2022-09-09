package rk.entertainment.filmy.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

// Class containing functions releated to Android Componenets
// Eg ConvertDpToPx, check internet, display dialog
object UIUtils {

    // Convert the density pixels (dp) to Pixel (px)
    @JvmStatic
    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    // Function to display snackbar for messages, error, etc.
    @JvmStatic
    fun displayMessage(context: Context?, message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun hideKeyboard(activity: Activity, view: View) {
        try {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            Logs.logException(e)
        }
    }

}