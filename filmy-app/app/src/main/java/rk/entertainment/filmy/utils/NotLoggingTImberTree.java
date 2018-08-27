package rk.entertainment.filmy.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

import static android.util.Log.ERROR;

public class NotLoggingTImberTree extends Timber.Tree {

    // This tree is used for Release Builds
    // which wont log data
    // except for Error and Warning

    private static final String CRASHLYTICS_KEY_PRIORITY = "priority";
    private static final String CRASHLYTICS_KEY_TAG = "tag";
    private static final String CRASHLYTICS_KEY_MESSAGE = "message";


    @Override
    protected void log(final int priority, final String tag, @NonNull final String message, final Throwable throwable) {
        // If the priority is Error or Warning
        // log data to your crash library
        if (priority == ERROR || priority == Log.WARN) {

            Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
            Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
            Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);

            if (throwable == null)
                Crashlytics.logException(new Exception(message));
             else
                Crashlytics.logException(throwable);
        }
    }
}
