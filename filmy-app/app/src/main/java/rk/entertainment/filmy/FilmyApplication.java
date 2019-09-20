package rk.entertainment.filmy;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class FilmyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
