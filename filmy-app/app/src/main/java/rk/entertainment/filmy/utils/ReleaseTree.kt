package rk.entertainment.filmy.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class ReleaseTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.ERROR || priority == Log.DEBUG) {
            if (tag != null)
                FirebaseCrashlytics.getInstance().setCustomKey("tag", tag)
            FirebaseCrashlytics.getInstance().setCustomKey("priority", priority)
            FirebaseCrashlytics.getInstance().log(message)

            if (throwable != null)
                FirebaseCrashlytics.getInstance().recordException(throwable)

        } else return
    }
}