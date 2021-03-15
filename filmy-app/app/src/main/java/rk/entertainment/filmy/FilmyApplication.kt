package rk.entertainment.filmy

import android.app.Application
import rk.entertainment.filmy.utils.ReleaseTree
import timber.log.Timber
import timber.log.Timber.DebugTree

class FilmyApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
        else
            Timber.plant(ReleaseTree())
    }
}