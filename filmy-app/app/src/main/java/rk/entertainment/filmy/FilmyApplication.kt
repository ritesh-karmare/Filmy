package rk.entertainment.filmy

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import rk.entertainment.filmy.utils.ReleaseTree
import timber.log.Timber
import timber.log.Timber.DebugTree

class FilmyApplication : MultiDexApplication() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        appContext = this

        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
        else
            Timber.plant(ReleaseTree())
    }
}