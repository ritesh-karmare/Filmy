package rk.entertainment.filmy.utils;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import rk.entertainment.filmy.BuildConfig;

public final class Logs {
    
    public static void v(String tag,String message) {
        if(BuildConfig.DEBUG) {
            android.util.Log.v(tag+"==> ",message);
        } else {
            FirebaseCrashlytics.getInstance().log(tag+": "+message);
        }
    }
    
    public static void i(String tag,String message) {
        if(BuildConfig.DEBUG) {
            android.util.Log.i(tag+"==> ",message);
        } else {
            FirebaseCrashlytics.getInstance().log(tag+": "+message);
        }
    }
    
    public static void d(String tag,String message) {
        if(BuildConfig.DEBUG) {
            android.util.Log.d(tag+"==> ",message);
        } else {
            FirebaseCrashlytics.getInstance().log(tag+": "+message);
        }
    }
    
    public static void e(String tag,String message) {
        if(BuildConfig.DEBUG) {
            android.util.Log.e(tag+"==> ",message);
        } else {
            FirebaseCrashlytics.getInstance().log(tag+": "+message);
        }
    }
    
    public static void logException(Exception e) {
        if(BuildConfig.DEBUG) {
            e.printStackTrace();
        } else {
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }
    
    public static void logException(Throwable t) {
        if(BuildConfig.DEBUG) {
            if(t != null) {
                t.printStackTrace();
            }
        } else {
            FirebaseCrashlytics.getInstance().recordException(t);
        }
    }
    
}
