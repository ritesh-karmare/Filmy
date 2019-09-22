package rk.entertainment.filmy.utils;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import rk.entertainment.filmy.BuildConfig;

public class AppLog {

    public static void i(String TAG, String message) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, "i: " + message);
        else
            Crashlytics.log(message);
    }

    public static void w(Exception exception) {
        if (exception == null) return;
        if (BuildConfig.DEBUG)
            exception.printStackTrace();
        else
            Crashlytics.logException(exception);
    }

    public static void e(Exception message) {
        if (message == null) return;
        if (BuildConfig.DEBUG)
            message.printStackTrace();
        else
            Crashlytics.logException(message);
    }

    public static void e(Throwable throwable) {
        if (throwable == null) return;
        if (BuildConfig.DEBUG)
            Log.getStackTraceString(throwable);
        else
            Crashlytics.logException(throwable);
    }
}
