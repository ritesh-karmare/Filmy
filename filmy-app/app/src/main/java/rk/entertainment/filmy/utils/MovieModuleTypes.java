package rk.entertainment.filmy.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MovieModuleTypes {

  /*
    NOW_PLAYING,
    UPCOMING_MOVIES,
    TOP_RATED,
    POPULAR
    */

    // Constants
    public static final int NOW_PLAYING = 1;
    public static final int UPCOMING = 2;
    public static final int TOP_RATED = 3;
    public static final int POPULAR = 4;

    // Declare the @ StringDef for these constants:
    @IntDef({NOW_PLAYING, UPCOMING, TOP_RATED, POPULAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MovieModule {
    }

}
