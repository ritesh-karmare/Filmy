package rk.entertainment.filmy;

import android.app.Application;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;
import rk.entertainment.filmy.utils.NotLoggingTImberTree;
import timber.log.Timber;

public class FilmyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initCrashlytics();
        initTimber();
    }

    private void initCrashlytics() {
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());
    }

    private void initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(@NonNull StackTraceElement element) {
                    return String.format("C:%s:%s",
                            super.createStackElementTag(element),
                            element.getLineNumber());
                }
            });
        else
            Timber.plant(new NotLoggingTImberTree());
    }
}
