package rk.entertainment.filmy

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class FilmyApplication : MultiDexApplication() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        appContext = this
    }
}