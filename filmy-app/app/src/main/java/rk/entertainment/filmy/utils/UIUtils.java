package rk.entertainment.filmy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.material.snackbar.Snackbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

// Class containing functions releated to Android Componenets
// Eg ConvertDpToPx, check internet, display dialog
public class UIUtils {

    // Convert the density pixels (dp) to Pixel (px)
    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    // Check whether Device is connected to internet or not
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null)
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Function to display snackbar for messages, error, etc.
    public static void displayMessage(Context context, boolean isSnackbar, String message, View view,
                                      boolean durationLong) {

        if (isSnackbar) {
            int duration = Snackbar.LENGTH_LONG;
            if (durationLong)
                duration = Snackbar.LENGTH_SHORT;
            Snackbar.make(view, message, duration).show();
        } else {
            int duration = Snackbar.LENGTH_LONG;
            if (durationLong)
                duration = Snackbar.LENGTH_SHORT;
            Toast.makeText(context, message, duration).show();
        }
    }
}